import requests
from bs4 import BeautifulSoup
from google.cloud import storage
from google.cloud import firestore
import os
import time

limit = 5

def read_news():
    try:
        r = requests.get('https://www.bmkg.go.id/tag/?tag=berita-utama&lang=ID')
        if r.status_code == 200:
            soup = BeautifulSoup(r.content, 'html.parser')
            posts = soup.findAll('div', class_='row margin-bottom-30')
            for post in posts[:limit]:
                link = str(post.find('a')['href']).replace('..', 'https://www.bmkg.go.id', 1)
                if link not in blacklist:
                    title = post.find('a').text
                    published = post.findAll('li')[0].text.strip()
                    author = post.findAll('li')[1].text.strip()
                    find_image = post.find('img', class_='img-responsive')['data-original']

                    blacklist.append(link)
                    
                    db = firestore.Client()
                    doc_ref = db.collection('posts').document()
                    doc_ref.set({
                        'title': title,
                        'link': link,
                        'published': published,
                        'author': author,
                        'image': find_image
                    })

                    with open('seen_posts.txt', 'a') as f:
                        f.write(link + '\n')
                        f.close()

                    blob.upload_from_filename('seen_posts.txt')

        time.sleep(43200)
        return

    except Exception as e:
        print(e)


def blacklisted_posts():
    if not os.path.isfile('seen_posts.txt'):
        blacklist = []
    else:
        with open('seen_posts.txt', 'r') as f:
            blacklist = f.read()
            blacklist = blacklist.split("\n")
            f.close()

    return blacklist


client = storage.Client()
bucket = client.get_bucket('b21cap0397')
blob = bucket.blob('seen_posts.txt')
blob.download_to_filename('seen_posts.txt')

blacklist = blacklisted_posts()
while True:
    read_news()