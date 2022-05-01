from django.db import models

class Board(models.Model):
    date = models.DateTimeField(auto_now_add=True)
    name = models.CharField(max_length=64)
    title = models.CharField(max_length=200)
    body = models.TextField()
    
    def __str__(self):
        return self.title
    
class Comment(models.Model):
    comment = models.TextField()
    date = models.DateTimeField(auto_now_add=True)
    board = models.ForeignKey(Board, null=True, blank=True, on_delete=models.CASCADE)
    
    def __str__(self):
        return self.comment