
from django.conf.urls import include 
from django.urls import path
from django.contrib import admin 
from rest_framework import routers 
from rest_framework.routers import DefaultRouter
from API import views
from .views import asdcreate
import re


urlpatterns = [
    path('<word>/',asdcreate),
]