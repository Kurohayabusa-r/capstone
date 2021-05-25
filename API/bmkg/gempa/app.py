from flask import Flask
from flask_restful import Api, Resource
import os
from db.models import query_all_data, GempaDirasakan, GempaLimaSR, GempaTerbaru


base_dir = os.path.abspath(os.path.dirname(__file__))
app = Flask(__name__)
api = Api(app)


def row_to_dict_mapper(lst):
    if lst:
        container = []
        for item in lst:
            keys = [x.name for x in item.__table__.columns]
            dct = {}
            for key in keys:
                dct[key] = item.__dict__.get(key)
            container.append(dct)
        return container
    return None


class Bmkg(Resource):
    def get(self, type):
        result = None
        if type == "terbaru":
            data = query_all_data(GempaTerbaru)
            result = row_to_dict_mapper(data)
            if result:
                return result

        elif type == "limasr":
            data = query_all_data(GempaLimaSR)
            result = row_to_dict_mapper(data)
            if result:
                return result

        elif type == "dirasakan":
            data = query_all_data(GempaDirasakan)
            result = row_to_dict_mapper(data)
            if result:
                return result
        else:
            return {"result": None}


api.add_resource(Bmkg, "/api/bmkg/gempa/<string:type>")


if __name__ == "__main__":
    app.run(debug=True)
