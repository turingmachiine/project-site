<#include "base.ftl"/>

<#macro title>Profiles</#macro>

<#macro content>
    <#list users as u>
        <p>${u}</p>
    </#list>
</#macro>

<@main/>