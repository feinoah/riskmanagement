<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<div class="messbox" ng-controller="gpsAlarmCtrl">
    <div class="warntitle"></div>
    <div class="warnContent">
        <div class="left">
            <div class="title"><span style="padding-left: 20px">报警时间</span><span>报警类型</span><span style="width: 50%">报警位置</span></div>
            <ul class="selectLi">
                <li ng-repeat="allCarInfo in allCarInfos"  ng-click="selectLi()"><span ng-bind="allCarInfo.Time" style="padding-left: 20px"></span><span ng-bind="allCarInfo.AlarmType"></span><span style="width: 50%" ng-bind="allCarInfo.Location"></span><i style="display: none" ng-bind="allCarInfo"></i></li>
            </ul>
        </div>
        <div class="right">
           <div id="posMap" ></div>
        </div>
    </div>
</div>