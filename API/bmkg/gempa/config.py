import os
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()
base_dir = os.path.abspath(os.path.dirname(__file__))
engine = create_engine(f"sqlite:////{base_dir}/db/bmkg_gempa.db", echo=True)