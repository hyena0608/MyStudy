from django.shortcuts import render, redirect, get_object_or_404
from .models import Board
from django.utils import timezone

def home(request):
    # 게시글을 최신 날짜 순으로 띄운다.
	posts = Board.objects.filter().order_by('-date')
	return render(request, 'index.html', {'posts': posts})

def new(request):
    return render(request, 'new.html')

def create(request):
	if(request.method == 'POST'):
		post = Board()
		post.title = request.POST['title']
		post.body = request.POST['body']
		post.date = timezone.now()
		post.save()
	
	return redirect('home')

def detail(request, post_id):
    post_detail = get_object_or_404(Board, pk=post_id)
    return render(request, 'detail.html', {'post_detail' : post_detail})