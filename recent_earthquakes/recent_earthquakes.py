import requests
from bs4 import BeautifulSoup
from google.cloud import storage
from google.cloud import firestore
import os
import time


def read_earthquake():
    try:
        r = requests.get('https://www.bmkg.go.id/gempabumi/gempabumi-terkini.bmkg')
        if r.status_code == 200:
            soup = BeautifulSoup(r.content, 'html.parser')
            table = soup.find('tbody')
            entries = table.findAll('tr')
            for entry in entries[:limit]:
                all_columns = entry.findAll('td')
                check = (all_columns[1].text + ',' + all_columns[-1].text).replace('<br>', '')
                if check not in blacklist:
                    occurence_time = all_columns[1].text.replace('<br>', '')
                    latitude = all_columns[2].text
                    longitude = all_columns[3].text
                    magnitude = all_columns[4].text
                    depth = all_columns[5].text
                    region = all_columns[6].text
                    # print(check)
                    # print("{} {} {} {} {} {}\n".format(occurence_time, latitude, longitude, magnitude, depth, region))

                    blacklist.append(check)
                    
                    db = firestore.Client()
                    doc_ref = db.collection('earthquakes').document()
                    doc_ref.set({
                        'occurence_time': occurence_time,
                        'latitude': latitude,
                        'longitude': longitude,
                        'magnitude': magnitude,
                        'depth': depth,
                        'region': region,
                        'time': time.ctime(int(time.time()))
                    })

                    with open('earthquake_history.txt', 'a') as f:
                        f.write(check + '\n')
                        f.close()

                    upload_to_gcs('earthquake_history.txt')
                    time.sleep(10)

        time.sleep(43200)
        return

    except Exception as e:
        print(e)


def blacklisted_posts():
    if not os.path.isfile('earthquake_history.txt'):
        blacklist = []
    else:
        with open('earthquake_history.txt', 'r') as f:
            blacklist = f.read()
            blacklist = blacklist.split("\n")
            f.close()

    return blacklist


def upload_to_gcs(filename):
    client = storage.Client()
    bucket = client.get_bucket('b21cap0397')
    blob = bucket.blob(filename)
    blob.upload_from_filename(filename)


def download_from_gcs(filename):
    client = storage.Client()
    bucket = client.get_bucket('b21cap0397')
    blob = bucket.blob(filename)
    blob.download_to_filename(filename)


limit = 10
download_from_gcs('earthquake_history.txt')
blacklist = blacklisted_posts()
while True:
    read_earthquake()