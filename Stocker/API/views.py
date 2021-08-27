from rest_framework import response
from rest_framework.generics import DestroyAPIView
from API.models import API
from django.shortcuts import render
from rest_framework.viewsets import ModelViewSet
from rest_framework.response import Response
from rest_framework.decorators import api_view
from django.http import HttpResponse
import os
from bs4 import BeautifulSoup
import requests
import pandas as pd


@api_view(['GET'])
def asdcreate(request , word):

    headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)Chrome/63.0.3239.132 Safari/537.36'}

    url = f"https://finance.naver.com/item/sise_day.nhn?code={word}"
    req =requests.get(url,headers=headers)


    soup = BeautifulSoup(req.text, 'html.parser')

    df=pd.DataFrame()


    for page in range(1,10):
        req = requests.get(url+f'&page={page}',headers = headers)
        df = pd.concat([df,pd.read_html(req.text, encoding = 'euc-kr')[0]], ignore_index = True)

        


    df.dropna(inplace = True)

    df.reset_index(drop = True, inplace =True)

    return HttpResponse(df)
 
    



