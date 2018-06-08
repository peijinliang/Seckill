//存放主要交互逻辑js代码
//javascript 模块化

var seckill = {

    //封装秒杀相关的ajax的url
    Url: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        exection: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + "/execution";
        }
    },

    handleSeckillkill: function (seckillId, node) {
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn"> 开始秒杀</button>');
        $.post(seckill.exposer(seckillId), {}, function (result) {
            //在回调函数中执行交互流程
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.Url.exection(seckillId, md5);

                    console.log("killUrl:" + killUrl);
                    //绑定一次点击事件 防止用户多次点击造成没有必要的请求
                    $('killBtn').one('click', function () {
                        //执行秒杀请求的操作

                        //1:先禁用按钮
                        $(this).addClass("disabled");
                        //2.发送秒杀请求,执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {

                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];

                                //3:显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }

                        })
                    })
                    node.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result:' + result);
            }
        });

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
            }).on('finish.countdown', function (seckillId, seckillbox) {
                seckill.handleSeckillkill(seckillId, seckillbox);
            })
        } else {
            //秒杀开始
            seckill.handleSeckillkill(seckillId, seckillbox);
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