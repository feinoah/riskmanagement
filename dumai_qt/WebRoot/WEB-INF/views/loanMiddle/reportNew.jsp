<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en" ng-app="myMark">
<head>
    <meta charset="UTF-8">
    <title>风控评审报告</title>
   	<link rel="stylesheet/less" href="${ctx}/static/css/markReport.less" type="text/less">
    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/static/css/jquery-ui.css">
    <link rel="stylesheet" href="${ctx}/static/css/style.css">
    <link rel="stylesheet" href="${ctx}/static/css/load.css">
    <link rel="stylesheet" href="${ctx}/static/css/ng-grid.css">
   	
   	<script charset="utf-8" type="text/javascript" src="${ctx}/static/script/lib/less.min.js"></script>
	<script src="${ctx}/static/script/lib/jquery.min.js"></script>
	<script src="${ctx}/static/script/lib/angular.min.js"></script>
	<script src="${ctx}/static/script/controller/markControll1.js"></script>
	<script charset="utf-8" type="text/javascript">
	    less.watch();
	</script>

</head>
<body>
<div class="markReport1" ng-class="{true:'show_1',false:'hide_1'}[reportFlag1]" ng-controller="markControll1">
    <div class="baseReport">
        <ul class="base_header">
            <li><img src="${ctx}/static/images/logo_x.png" alt="logo"></li>
            <div class="base_right">
                <li><span>编号：</span><span>XJJHDJSK</span></li>
                <li><span>日期：</span><span>2017-04-01</span></li>
                <li><a href="javascript:;">导出</a><span ng-click="closeReport1()" class="report_close"></span></li>
            </div>


        </ul>
        <div class="base_title"><p>读脉风控评审报告</p> <span></span></div>
        <ul class="base_list">
            <div class="soloInfo">
                <p class="solo_title"><span></span>基本信息</p>
                <div class="basicInfo">
                    <ul>
                        <li><span>姓名：</span><span ng-bind="basics.name">111</span></li>
                        <li><span>性别：</span><span ng-bind="basics.sex">222</span></li>
                        <li><span>年龄：</span><span ng-bind="basics.age">333</span></li>
                        <li><span>婚姻（查询）：</span><span ng-bind="basicDetail.maritalStatus">444</span></li>
                        <li><span>学历：</span><span ng-bind="basicDetail.education">555</span></li>
                        <li><span>身份证号码：</span><span ng-bind="basics.card_num">666</span></li>
                        <li><span>手机号码：</span><span ng-bind="basics.mobile"></span></li>
                        <li><span>银行卡：</span><span ng-bind="basics.bank_num"></span></li>
                        <li><span>职业情况 ：</span><span ng-bind="basicDetail.department"></span></li>
                        <li><span>常住地址：</span><span ng-bind="changAddress"  style="width:78%"></span></li>
                        <li><span>固定收入 ：</span><span ng-bind="basics.sqje"></span></li>
                        <li><span>紧急联系人1：</span><span ng-bind="basics.linkname1"></span><span ng-bind="basics.linkphone1"></span><span ng-bind="basics.linkReation1"></span></li>
                        <li><span>紧急联系人2：</span><span ng-bind="basics.linkname2"></span><span ng-bind="basics.linkphone2"></span><span ng-bind="basics.linkReation2"></span></li>
                        <ul class="generated">
                            <li ng-repeat="aa in arrays"><span ng-cloak>{{aa.titleName}} ：</span><span>{{aa.titleContent}}</span></li>
                        </ul>
                    </ul>
                    <img src="./image/photosh.png" alt="">
                    <!--<img src="data:image/gif;base64,{{peoImg}}" alt={{basics.name}}{{peoImg}} ng-cloak>-->
                </div>
            </div>
            <div class="fraudRule">
                <p class="fraudRule_title"><span></span>风控规则审核结果</p>
                <ul>
                    <li><span>风控规则</span><span>结果</span><span>结果描述</span></li>
                    <li ng-repeat="use in users"><span ng-bind="use.id"></span><span ng-bind="use.type"></span><span ng-bind="use.time"></span></li>
                </ul>
            </div>
            <div class="fraudScore">
                <div class="fraudScore_title" >
                    <span></span>反欺诈模型审核结果：
                    <h5><i>得分：</i><em ng-bind="score"></em><span></span></h5>
                </div>
                <ul>
                    <li class="firstLi firstLi1"><span>结果</span><span>结果描述</span>
                        <i class="model_unfold on" title="加载更多" ng-click="closeLiList1('.model_unfold')"></i>
                    </li>
                    <li ng-repeat="use in users"><span ng-bind="use.type"></span><span ng-bind="use.time"></span></li>
                </ul>
            </div>
            <div class="creditCheck">
                <div class="creditCheck_title" >
                    <span></span>信用评分模型审核结果：</i>
                    <h5><i>得分：</i><em ng-bind="score"></em><span></span></h5>
                </div>
                <ul>
                    <li class="firstLi firstLi2"><span>结果</span><span>结果描述</span>
                        <i class="credit_unfold on" title="加载更多" ng-click="closeLiList2('.credit_unfold')"></i>
                    </li>
                    <li ng-repeat="use in users"><span ng-bind="use.type"></span><span ng-bind="use.time"></span></li>
                </ul>
            </div>
            <div class="phoneCheck">
                <div class="phoneCheck_title" >
                    <span></span>电核审核结果：</i>
                    <h5><i>得分：</i><em ng-bind="score"></em><span></span></h5>
                </div>
                <ul>
                    <li class="firstLi firstLi3 title"><span>电核项</span><span>描述</span><span>结果</span><span>电核内容</span><span>备注</span><i class="phone_unfold on" title="加载更多" ng-click="closeLiList3('.phone_unfold')"></i>
                    </li>
                    <li ng-repeat="use in users"><span ng-bind="use.id"></span><span ng-bind="use.type"></span><span ng-bind="use.time"></span><span ng-bind="use.time"></span><span ng-bind="use.time"></span></li>
                </ul>
                <div class="solution">
                    <span>电核的基本情况:</span>
                    <span ng-bind="content"></span>
                </div>
                <div class="solution">
                    <span>异常情况:</span>
                    <span ng-bind="content"></span>
                </div>
            </div>
            <div class="detailList">
                <p><span></span>原始数据查询</p>
                <ul>
                    <li ng-repeat="report in reports" ><a href="javascript:;" ng-bind="report.name" ng-click="detailReport()"></a></li>
                </ul>
            </div>

        </ul>
    </div>
</div>
	
</body>
</html>