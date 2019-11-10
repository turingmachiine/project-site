<#include "base.ftl">

<#macro title>Posts</#macro>

<#macro content>
        <div class="page-blog bg--white section-padding--lg blog-sidebar right-sidebar">
        	<div class="container">
        		<div class="row">
        			<div class="col-lg-9 col-12">
        				<div class="blog-page">
        					<div class="page__header">
        						<h2>Посты</h2>
        					</div>
        					<!-- Start Single Post -->
        					<#list posts as p>
        					    <article class="blog__post d-flex flex-wrap">
        						    <div class="thumb">
        							    <a href="/post?id=${p.id}">
											<#if p.image?has_content>
												<img src="${p.image}" alt="no photo">
											<#else>
												<img src="/assets/images/blog/blog-3/1.jpg" alt="no photo">
											</#if>
        							    </a>
        						    </div>
        						    <div class="content">
        							    <h4><a href="/post?id=${p.id}">${p.title}</a></h4>
        							    <ul class="post__meta">
        								    <li>${p.author.firstName} ${p.author.lastName}</li>
											<li class="post_separator"></li>
											<li>${p.postDate}</li>
        							    </ul>
        							    <p>${p.postText}</p>
        							    <div class="blog__btn">
        								    <a class="shopbtn" href="/post?id=${p.id}">прочитать полностью</a>
        							    </div>
        						    </div>
        					    </article>
                                </#list>
        					<!-- End Single Post -->
        				</div>
        			</div>
        			<div class="col-lg-3 col-12 md-mt-40 sm-mt-40">
        				<div class="wn__sidebar">
        					<!-- Start Single Widget -->
        					<!-- End Single Widget -->
        					<!-- Start Single Widget -->
        					<!-- End Single Widget -->
        					<!-- Start Single Widget -->
        					<!-- End Single Widget -->
        					<!-- Start Single Widget -->
        					<#--<aside class="widget archives_widget">
        						<h3 class="widget-title">Тэги</h3>
        						<ul>
        						    <#list tags as t>
                                        <li><a href="#">${t.name}</a></li>
                                    </#list>
        						</ul>
        					</aside>
-->
        					<aside class="widget category_widget">
        						<h3 class="widget-title">Посты о доме</h3>
        						<ul>
        						    <#list houses as h>
        							    <li><a href="/posts?house_id=${h.id}">${h.name}</a></li>
        						    </#list>
                                 </ul>
        					</aside>
        					<!-- End Single Widget -->
        					<!-- Start Single Widget -->
        					<!-- End Single Widget -->
        				</div>
        			</div>
        		</div>
        	</div>
        </div>
</#macro>
<@main/>