import xml.etree.ElementTree as ET
from helper.date_helper import month_mapper


def get_gempa_id(tanggal, jam) -> str:
    tgl_lst = tanggal.split()
    tgl_lst[1] = month_mapper.get(tgl_lst[1])
    tgl_lst[0], tgl_lst[2] = tgl_lst[2], tgl_lst[0]
    tgl_str = "".join(tgl_lst)
    jam_str = "".join(jam.split()[0].split(":"))
    return f"{tgl_str}{jam_str}"


def parse_gempa_terbaru(response) -> list:
    container = []
    xml = ET.fromstring(response.text)
    for gempa in xml.findall('gempa'):
        tanggal = gempa.find('Tanggal').text
        jam = gempa.find('Jam').text
        gempa_id = get_gempa_id(tanggal, jam)

        koordinat = gempa.find('point').find('coordinates').text
        magnitude = gempa.find('Magnitude').text
        kedalaman = gempa.find('Kedalaman').text
        wilayah = gempa.find('Wilayah').text
        potensi = gempa.find('Potensi').text
        dirasakan = gempa.find('Dirasakan').text

        container.append([gempa_id, tanggal, jam, koordinat,
                          magnitude, kedalaman, wilayah, potensi, dirasakan])

    return container


def parse_gempa_5sr(response) -> list:
    container = []
    xml = ET.fromstring(response.text)
    for gempa in xml.findall('gempa'):
        tanggal = gempa.find('Tanggal').text
        jam = gempa.find('Jam').text
        gempa_id = get_gempa_id(tanggal, jam)

        koordinat = gempa.find('point').find('coordinates').text
        magnitude = gempa.find('Magnitude').text
        kedalaman = gempa.find('Kedalaman').text
        wilayah = gempa.find('Wilayah').text
        potensi = gempa.find('Potensi').text

        container.append([gempa_id, tanggal, jam, koordinat,
                          magnitude, kedalaman, wilayah, potensi])

    return container


def parse_gempa_dirasakan(response) -> list:
    container = []
    xml = ET.fromstring(response.text)
    for gempa in xml.findall('gempa'):
        tanggal = gempa.find('Tanggal').text
        jam = gempa.find('Jam').text
        gempa_id = get_gempa_id(tanggal, jam)

        koordinat = gempa.find('point').find('coordinates').text
        magnitude = gempa.find('Magnitude').text
        kedalaman = gempa.find('Kedalaman').text
        wilayah = gempa.find('Wilayah').text
        dirasakan = gempa.find('Dirasakan').text

        container.append([gempa_id, tanggal, jam, koordinat,
                          magnitude, kedalaman, wilayah, dirasakan])

    return container
