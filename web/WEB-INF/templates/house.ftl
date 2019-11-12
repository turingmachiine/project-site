<#include "base.ftl">

<#macro title>${house.name}</#macro>

<#macro content>
    <div class="maincontent bg--white pt--80 pb--55">
        <div class="container">
            <div class="row">
                <div class="col-lg-9 col-12">
                    <div class="wn__single__product">
                        <div class="row">
                            <div class="col-lg-6 col-12">
                                <div class="wn__fotorama__wrapper">
                                    <div class="fotorama wn__fotorama__action" data-nav="thumbs">
                                        <#if house.image?has_content>
                                            <a href="${house.image}"><img src="${house.image}" alt=""></a>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-12">
                                <div class="product__info__main">
                                    <h1>${house.name}</h1>
                                    <div class="product-reviews-summary d-flex">
                                        <#if house.houseClass.id == 1>
                                            <ul class="rating d-flex">
                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                            </ul>
                                        <#elseif house.houseClass.id == 2>
                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                            <li><i class="fa fa-star-o"></i></li>
                                            <li><i class="fa fa-star-o"></i></li>
                                        <#else>
                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                            <li><i class="fa fa-star-o"></i></li>
                                            <li><i class="fa fa-star-o"></i></li>
                                            <li><i class="fa fa-star-o"></i></li>
                                            <li><i class="fa fa-star-o"></i></li>
                                        </#if>
                                        <div class="reviews-actions d-flex">
                                            <p>Creator: ${house.creator.firstName} ${house.creator.lastName}</p>
                                        </div>
                                    </div>
                                    <div class="product__overview">
                                        <p>${house.description}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="wn__related__product pt--80 pb--50">
                        <div class="section__title text-center">
                            <h2 class="title__be--2">Дома этого пользователя</h2>
                        </div>
                        <div class="row mt--60">
                            <div class="productcategory__slide--2 arrows_style owl-carousel  owl-theme">
                                <#if otherHouses?has_content>
                                    <#list otherHouses as house>
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                            <div class="product">
                                                <div class="row justify-content-between">
                                                    <div class=" col-4 product__thumb">
                                                        <#if house.image?has_content>
                                                            <a class="first__img" href="/house?id=${house.id}"><img src="${house.image}" alt="${house.name}"></a>
                                                        <#else>
                                                            <a class="first__img" href="/house?id=${house.id}"><img src="/assets/images/blog/blog-3/1.jpg"></a>
                                                        </#if>
                                                    </div>
                                                    <div class="col-8 product__content">
                                                        <h4><a href="/house?id=${house.id}">${house.name}</a></h4>
                                                        <#if house.houseClass.id == 1>
                                                            <ul class="rating d-flex">
                                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                                <li class="on"><i class="fa fa-star-o"></i></li>
                                                            </ul>
                                                        <#elseif house.houseClass.id == 2>

                                                        <ul class="rating d-flex">
                                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                                            <li><i class="fa fa-star-o"></i></li>
                                                            <li><i class="fa fa-star-o"></i></li>
                                                        </ul>
                                                            <#else>
                                                        <ul class="rating d-flex">
                                                            <li class="on"><i class="fa fa-star-o"></i></li>
                                                            <li><i class="fa fa-star-o"></i></li>
                                                            <li><i class="fa fa-star-o"></i></li>
                                                            <li><i class="fa fa-star-o"></i></li>
                                                            <li><i class="fa fa-star-o"></i></li>
                                                        </ul>
                                                        </#if>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </#if>
                                <!-- Start Single Product -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-12 md-mt-40 sm-mt-40">
                </div>
            </div>
        </div>
    </div>
</#macro>

<@main/>