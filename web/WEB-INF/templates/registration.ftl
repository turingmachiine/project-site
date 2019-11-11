<#include "base.ftl"/>

<#macro title>Registration</#macro>

<#macro content>

    <section class="my_account_area pt--80 pb--55 bg--white">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6 col-12">
                    <div class="my__account__wrapper">
                        <h3 class="account__title">Регистрация</h3>
                        <form action="/registration" method="post" enctype="multipart/form-data">
                            <div class="account__form">
                                <div class="image-container">
                                    <img src="http://placehold.it/150x150" id="imgProfile" style="width: 150px; height: 150px" class="img-thumbnail" />
                                    <div class="middle">
                                        <input type="button" class="btn btn-secondary" id="btnChangePicture" value="Добавить" >
                                        <input type="file" style="display: none;" id="profilePicture" name="file" />
                                        <br><input type="button" class="btn btn-secondary btn-sm mt-1 d-none" id="btnDiscard" value="Discard Changes"/>
                                    </div>
                                </div>
                                <div class="input__box">
                                    <label>Email<span>*</span></label>
                                    <input name="email" type="email" >
                                </div>
                                <div class="input__box">
                                    <label>Пароль<span>*</span></label>
                                    <input id="password" type="password" name="password" oninput="pass_check()">
                                    <label style="color: #bd2130" id="length_error"></label>
                                    <label style="color: #bd2130" id="num_error"></label>
                                    <label style="color: #bd2130" id="letter_error"></label>
                                </div>

                                <div class="input__box">
                                    <label>Имя<span>*</span></label>
                                    <input name="first_name" type="text">
                                </div>
                                <div class="input__box">
                                    <label>Фамилия<span>*</span></label>
                                    <input name="last_name" type="text">
                                </div>
                                <div class="form__btn mx-auto">
                                    <button type="submit">Зарегистрироваться</button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</#macro>

<@main/>