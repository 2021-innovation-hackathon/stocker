from django.db import models

# Create your models here.

class API(models.Model):
       list=models.TextField()

       def __str__(self):
           return self.list
