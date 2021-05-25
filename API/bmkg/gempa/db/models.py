import os
from sqlalchemy import Column, Integer, String, desc
from sqlalchemy.orm import sessionmaker
from config import Base, base_dir, engine


class GempaTerbaru(Base):
    __tablename__ = "gempa_terbaru"
    id = Column('id', Integer, primary_key=True)
    tanggal = Column('tanggal', String)
    jam = Column('jam', String)
    koordinat = Column('koordinat', String)
    magnitude = Column('magnitude', String)
    kedalaman = Column('kedalaman', String)
    wilayah = Column('wilayah', String)
    potensi = Column('potensi', String)
    dirasakan = Column('dirasakan', String)

    def __repr__(self):
        return f"<GempaTerbaru(id={self.id},tanggal={self.tanggal},jam={self.jam},...)>"


class GempaLimaSR(Base):
    __tablename__ = "gempa_lima_sr"
    id = Column('id', Integer, primary_key=True)
    tanggal = Column('tanggal', String)
    jam = Column('jam', String)
    koordinat = Column('koordinat', String)
    magnitude = Column('magnitude', String)
    kedalaman = Column('kedalaman', String)
    wilayah = Column('wilayah', String)
    potensi = Column('potensi', String)

    def __repr__(self):
        return f"<GempaLimaSR(id={self.id},tanggal={self.tanggal},jam={self.jam},...)>"


class GempaDirasakan(Base):
    __tablename__ = "gempa_dirasakan"
    id = Column('id', Integer, primary_key=True)
    tanggal = Column('tanggal', String)
    jam = Column('jam', String)
    koordinat = Column('koordinat', String)
    magnitude = Column('magnitude', String)
    kedalaman = Column('kedalaman', String)
    wilayah = Column('wilayah', String)
    dirasakan = Column('dirasakan', String)

    def __repr__(self):
        return f"<GempaDirasakan(id={self.id},tanggal={self.tanggal},jam={self.jam},...)>"


def create_db(db_name):
    if not os.path.exists(os.path.join(base_dir, db_name)):
        Base.metadata.create_all(bind=engine)


def insert_data(entity):
    Session = sessionmaker(bind=engine)
    session = Session()
    session.add(entity)
    session.commit()
    session.close()


def query_all_data(entity):
    result = None
    Session = sessionmaker(bind=engine)
    session = Session()
    result = session.query(entity).order_by(desc(entity.id)).all()
    session.close()
    return result


def query_data_by_id(entity, gempa_id):
    result = None
    Session = sessionmaker(bind=engine)
    session = Session()
    result = session.query(entity).filter_by(id=gempa_id).first()
    session.close()
    return result


def delete_all_data(entity):
    Session = sessionmaker(bind=engine)
    session = Session()
    session.query(entity).delete(synchronize_session=False)
    session.commit()
    session.close()
