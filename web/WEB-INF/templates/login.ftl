<#include "base.ftl"/>

<#macro title>Login</#macro>


<#macro content>
    <section class="my_account_area pt--80 pb--55 bg--white">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6 col-12">
                    <div class="my__account__wrapper">
                        <h3 class="account__title">Login</h3>
                        <form action="/login" method="post">
                            <div class="account__form">
                                <div class="input__box">
                                    <label>Email<span>*</span></label>
                                    <input type="text" name="login">
                                </div>
                                <div class="input__box">
                                    <label>Пароль<span>*</span></label>
                                    <input id="password" type="password" name="password" oninput="pass_check()">
                                    <label style="color: #bd2130" id="length_error"></label>
                                    <label style="color: #bd2130" id="num_error"></label>
                                    <label style="color: #bd2130" id="letter_error"></label>
                                </div>
                                <div class="form__btn">
                                    <button>Войти</button>
                                    <label class="label-for-checkbox">
                                        <input id="rememberme" class="input-checkbox" name="rememberme" type="checkbox">
                                        <span>Запомнить меня</span>
                                    </label>
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