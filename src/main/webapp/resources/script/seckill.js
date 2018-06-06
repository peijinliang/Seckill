//存放主要交互逻辑js代码
//javascript 模块化

var seckill = {

    //封装秒杀相关的ajax的url
    Url: {
        now: function () {
            return '/seckill/time/now';
        }
    },

    // 验证手机号
    ValidatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    //进行计时操作
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillbox = $('#seckill-box');

        //秒杀结束
        if (nowTime > endTime) {
            seckillbox.html('秒杀结束了!');
        } else if (nowTime < startTime) {
            //秒杀尚未开始,计时绑定事件
            var killTime = new Date(startTime + 1000);
            seckillbox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
                seckillbox.html(format);
            })
        } else {

            //秒杀开始
        }

    },

    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登陆,计时交互
            //规划我们的交互流程
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['startTime'];

            //验证手机号
            if (!seckill.ValidatePhone(killPhone)) {
                //绑定phone
                //控制输出
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,   //显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false     //关闭键盘事件
                });

                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.ValidatePhone(inputPhone)) {
                        //电话写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新界面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }

            //已经登陆
            //计时交互
            $.get(seckill.Url.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    //计时服务判断
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result)
                }
            })

        }
    }

}