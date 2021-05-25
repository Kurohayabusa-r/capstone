import requests

from db.models import insert_data, GempaLimaSR, query_data_by_id, create_db, GempaDirasakan, delete_all_data, \
    GempaTerbaru
from helper.xml_parser import parse_gempa_terbaru, parse_gempa_5sr, parse_gempa_dirasakan


url_gempa_terbaru = "https://data.bmkg.go.id/DataMKG/TEWS/autogempa.xml"  # Newest
url_gempa_5sr = "https://data.bmkg.go.id/DataMKG/TEWS/gempaterkini.xml"  # >= 5.0
url_gempa_dirasakan = "https://data.bmkg.go.id/DataMKG/TEWS/gempadirasakan.xml"  # Dirasakan


def send_request(url):
    res = None
    try:
        res = requests.get(url)
    except:
        pass
    return res


def get_gempa_terbaru(url):
    response = send_request(url)
    parsed_response = parse_gempa_terbaru(response)
    delete_all_data(GempaTerbaru)
    for item in parsed_response:
        gempa_terbaru = GempaTerbaru()
        gempa_terbaru.id = item[0]
        gempa_terbaru.tanggal = item[1]
        gempa_terbaru.jam = item[2]
        gempa_terbaru.koordinat = item[3]
        gempa_terbaru.magnitude = item[4]
        gempa_terbaru.kedalaman = item[5]
        gempa_terbaru.wilayah = item[6]
        gempa_terbaru.potensi = item[7]
        gempa_terbaru.dirasakan = item[8]

        insert_data(gempa_terbaru)


def get_gempa_5sr(url):
    response = send_request(url)
    parsed_response = parse_gempa_5sr(response)
    for item in parsed_response:
        result = query_data_by_id(GempaLimaSR, item[0])
        if result:
            continue
        gempa_5sr = GempaLimaSR()
        gempa_5sr.id = item[0]
        gempa_5sr.tanggal = item[1]
        gempa_5sr.jam = item[2]
        gempa_5sr.koordinat = item[3]
        gempa_5sr.magnitude = item[4]
        gempa_5sr.kedalaman = item[5]
        gempa_5sr.wilayah = item[6]
        gempa_5sr.potensi = item[7]

        insert_data(gempa_5sr)


def get_gempa_dirasakan(url):
    response = send_request(url)
    parsed_response = parse_gempa_dirasakan(response)
    for item in parsed_response:
        result = query_data_by_id(GempaDirasakan, item[0])
        if result:
            continue
        gempa_dirasakan = GempaDirasakan()
        gempa_dirasakan.id = item[0]
        gempa_dirasakan.tanggal = item[1]
        gempa_dirasakan.jam = item[2]
        gempa_dirasakan.koordinat = item[3]
        gempa_dirasakan.magnitude = item[4]
        gempa_dirasakan.kedalaman = item[5]
        gempa_dirasakan.wilayah = item[6]
        gempa_dirasakan.dirasakan = item[7]

        insert_data(gempa_dirasakan)


def main():
    create_db("bmkg_gempa.db")
    get_gempa_terbaru(url_gempa_terbaru)
    get_gempa_5sr(url_gempa_5sr)
    get_gempa_dirasakan(url_gempa_dirasakan)


if __name__ == "__main__":

    main()
