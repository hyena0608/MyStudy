import imp
from multiprocessing.spawn import old_main_modules
from django.shortcuts import render, redirect, get_object_or_404
from .models import Board, Comment
from django.utils import timezone
from django.core.paginator import Paginator


def home(request):
    # 게시글을 최신 날짜 순으로 띄운다.
    posts = Board.objects.filter().order_by('-date')
    paginator = Paginator(posts, 5)
    pagnum = request.GET.get('page')
    posts = paginator.get_page(pagnum)
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
    comment_detail = get_object_or_404(Board, pk=post_id) # 댓글 뿌려주기 추가
    return render(request, 'detail.html', {'post_detail': post_detail, 'comment_detail': comment_detail})

# 댓글 작성 기능
def create_comment(request, post_id):
    comment = Comment()
    comment.comment = request.POST['comment']
    comment.date = timezone.now()
    comment.board = get_object_or_404(Board, pk=post_id)
    comment.save()
    detail(request, post_id)
    return redirect('detail', post_id)