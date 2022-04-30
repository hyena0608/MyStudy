from django.shortcuts import render, redirect
from .models import Board
from django.utils import timezone

def home(request):
	return render(request, 'new.html')

def create(request):
	if(request.method == 'POST'):
		post = Board()
		post.title = request.POST['title']
		post.body = request.POST['body']
		post.date = timezone.now()
		post.save()
	
	return redirect('home')