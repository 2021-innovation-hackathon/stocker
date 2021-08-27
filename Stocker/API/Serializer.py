from rest_framework import serializers
from .models import API


class APISerializers(serializers.ModelSerializer):
    class Meta :
        model = API
        fields='__all__'