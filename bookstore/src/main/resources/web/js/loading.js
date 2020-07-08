$(function() {
    var LOGIN_URL = "http://localhost:8088/login"
    var BASE_URL = "http://localhost:8088/bookstore";
    var userToken = $.session.get('token');
    login();
    fly();
    init(1);

    /**
     * 初次加载
     */
    function init(pageNumber) {
        $.ajax({
            url: BASE_URL + "/book",
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            headers: {
                "authorization": userToken
            },
            data: {
                "page": pageNumber
            },
            success: function(res) {
                console.log(res);
                if (res.status == 'success') {
                    addBook(res.data);
                    pagination(res.data);
                    detail();
                } else {
                    alert(res.data.errorMsg, null, function () {
                        $('#login').modal();
                    }, {type: 'error', confirmButtonText: 'OK'});
                }
            }
            
        })
    }

    

    function addBook(data) {
        $('.content .books ul').html("");
        for(let i = data.list.length -1; i >= 0 ; i--) {
            $('.content .books ul').prepend('<li><dl>' +
                                        '<dd class="popupTrigger" data-popup-target="demoPopup"><input class="book_id" type="hidden" value="' + data.list[i].bookId +'"/><a>' +
                                        '<img class="book_img" src="' + data.list[i].bookImg + '" alt="book" /></a></dd>' +
                                        '<dt><p class="book_title"><a class="popupTrigger" data-popup-target="demoPopup"' +
                                        '" target="_blank">' + data.list[i].bookName + '</a></p>' +
                                        '<p class="book_inline">by <span class="author">' + data.list[i].bookAuthor + '</span></p>' +
                                        '<p class="book_inline">库存: <span class="remain">' + data.list[i].bookRemain + 
                                        '</span></br>售价: <i class="fa fa-rmb"></i><span class="price">' + data.list[i].bookPrice + '</span></p>' +
                                        '<p class="book_inline"></p>' +
                                        '<div class="book_buy">' + 
                                        '<button type="button" class="default" onmousedown="ripplet(event)">加入购物车</button></div>' +
                                        // '<span class="cut"><i class="fa fa-minus-square-o"></i></span>\n' + 
                                        // '<span class="buy_num">0</span>\n' +
                                        // '<span class="add"><i class="fa fa-plus-square-o"></i></span>\n</div>' + 
                                        '</dt></dl></li>');
        }
        // console.log("dom已生成")
        

        // 当书名或者作者名过长时用省略号代替超长部分
        $(".author, .book_title").each(function(){
            var maxwidth = 15;
            if($(this).text().length>maxwidth){
                $(this).text($(this).text().substring(0,maxwidth));
                $(this).html($(this).html()+'...');
            }
        })

        // 点击飞入购物车动画
        $(".content .books ul .book_buy").click(function(){
            let flag = true;
            $(this).parent().parent().find(".book_img").animate_from_to(".checkout");
            let book_name = $(this).parent().find('.book_title a').text();
            console.log(book_name);
            let book_id = $(this).parent().parent().find('.book_id').val();
            console.log(book_id);
            let book_price = $(this).parent().find('.price').text();
            console.log(book_price);
            $(".checkout__summary tbody tr").each(function() {
                let id = $(this).find(".book_id").val();
                // console.log(id);
                if (book_id == id) {
                    flag = false;
                    return;
                }
            });
            if (flag) {
                $(".checkout__summary tbody").prepend('<tr><input class="book_id" type="hidden" value="' + book_id +'"/>' +
                                                        '<td><span class="delete delete_' + book_id + '"><i class="fa fa-trash-o fa-fw"></i> </span></td><td><span class="goods_name">' + book_name +
                                                        '</span><span class="muti"><i class="fa fa-close fa-fw"></i> </span><span class="goods_num">1</span></td>' +
                                                        '<td><i class="fa fa-rmb"></i> <span class="goods_price">' + book_price + '</span></td>' + 
                                                        '<td><span class="cut cut_' + book_id + '"> <i class="fa fa-minus-square-o"></i></span> <span class="add add_' + book_id + '"><i class="fa fa-plus-square-o"></i></span></td></tr>');
                cart(book_id);
            }
            
        })
        
    }
    cart();

    function detail() {
        $('.popupTrigger').click(function() {
            var detail_id = $(this).parent().find(".book_id").val();
            var a = $(this).text();
            console.log("0:" + detail_id);
            var url = BASE_URL + "/book/" + detail_id;
            console.log(detail_id);
            $.ajax({
                url: url,
                type: "GET",
                headers: {
                    "authorization":userToken
                },
                contentType: "application/json;charset=UTF-8",
                success: function(res) {
                    console.log(res);
                    if (res.status == 'success') {
                        res.data.bookIntro = res.data.bookIntro.replace("\r\n","<br>");
                        res.data.bookIntro = res.data.bookIntro.replace("\n","<br>");
                        $(".pop_books .popup__content").html('<div class="pop_top">' +
                        '<div class="pop_left">' +
                            '<img class="pop_img" src="' + res.data.bookImg + '" alt="book" />' +
                        '</div>' +
                        '<div class="pop_right">' +
                            '<input id="book_id" type="hidden" value="' + res.data.bookId + '"/>' +
                            '<p class="pop_title">' + res.data.bookName + '</p>' +
                            '<p class="pop_inline">by <span class="author">' + res.data.bookAuthor + '</span></p>' +
                            '<p class="pop_inline">售价： <span class="author">' + res.data.bookPrice + '</span></p>' +
                            '<p class="pop_inline">出版社： <span class="author">' + res.data.bookPublisher + '</span></p>' +
                            '<p class="pop_inline">类型： <span class="author">' + res.data.bookType + '</span></p>' +
                            '<p class="pop_inline">销量： <span class="author">' + res.data.bookSold + '</span></p>' +
                            '<p class="pop_inline">库存： <span class="author">' + res.data.bookRemain + '</span></p>' +
                        '</div>' +
                    '</div>' +
                    '<div class="pop_bottom">' +
                        '<p class="pop_inline">内容简介：</p>' +
                        '<p class="pop_content">' +
                            res.data.bookIntro +
                        '</p>' +
                        '<p class="pop_inline"></p>' +
                    '</div>')
                    } else {
                        alert(res.data.errorMsg, null, function () {
                        }, {type: 'error', confirmButtonText: 'OK'});
                    }
                }
            })
        })
        $('.popupTrigger').popup({
        });
    }

    // 分页相关
    function pagination(data) {
        
        $(".pagination-outer>ul").html('<li class="page-item pre"><a class="page-link" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
        if (data.pages <= 5) {
            for (let i = 1; i <= data.pages; i++) {
                $(".pagination-outer>ul").append('<li class="page-item num"><a class="page-link">' + i + '</a></li>')
            }
        } else {
            let begin = data.current - 2;
            let end = data.current + 2;
            if (begin <= 0) {
                begin = 1;
                end = begin + 4;
            }
            if (end > data.pages) {
                end = data.pages;
                begin = end - 4;
            }
            for (let i = begin; i <= end; i++) {
                $(".pagination-outer>ul").append('<li class="page-item num"><a class="page-link">' + i + '</a></li>')
            }
        }
        
        $(".pagination-outer>ul").append('<li class="page-item next"><a class="page-link" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');

        $(".pagination li").each(function() {
            let page = $(this).find("a").text();
            if (data.current == page) {
                $(this).addClass("active");
            }
        });

        $(".pagination li[class*=num]").click(function() {
            let page = $(this).find("a").text();
            // console.log(page);
            init(page);
        });

        $(".pagination li[class*=pre]").click(function() {
            let pre = data.current - 1;
            if (data.current == 1) {
                pre = data.current;
            }
            init(pre);
        });
        
        $(".pagination li[class*=next]").click(function() {
            let next = data.current + 1;
            if (data.current == data.pages) {
                next = data.current;
            }
            init(next);
        })
    }

    // 购物车
    function cart(book_id) {
        total();
        console.log(book_id);
        let add = ".add_" + book_id;
        let cut = ".cut_" + book_id;
        let del = ".delete_" + book_id;
        // 加减点击事件
        $(add).click(function() {
            var t = $(this).parent().parent().find("span[class*=goods_num]");
            var buy_num = t.text();
            console.log(buy_num);
            buy_num++;
            t.text(buy_num);
            total();
        });
        $(cut).click(function() {
            var t = $(this).parent().parent().find("span[class*=goods_num]");
            var buy_num = t.text();
            if (buy_num > 1) {
                buy_num--;
            }
            t.text(buy_num);
            total();
        });
        // 删除事件
        $(del).click(function() {
            var t = $(this).parent().parent();
            t.html("");
            total();
        })
        // 合计
        function total() {
            var total = 0;
            $(".checkout__summary tbody tr").each(function() {
                let price = $(this).find(".goods_price").text();
                let num = $(this).find(".goods_num").text();
                total += price*num;
                // console.log(price + '*' + num);
            });
            total = total.toFixed(2);
            // console.log(total);

            $(".checkout__total").text(total);
        }
    }

    // 购物车动画
    function fly() {
        [].slice.call( document.querySelectorAll( '.checkout' ) ).forEach( function( el ) {
            var openCtrl = el.querySelector( '.checkout__button' ),
                closeCtrls = el.querySelectorAll( '.checkout__cancel' );

            openCtrl.addEventListener( 'click', function(ev) {
                ev.preventDefault();
                classie.add( el, 'checkout--active' );
            } );

            [].slice.call( closeCtrls ).forEach( function( ctrl ) {
                ctrl.addEventListener( 'click', function() {
                    classie.remove( el, 'checkout--active' );
                } );
            } );
        } );
    }

    function login() {
        if (userToken != null) {
            $.ajax({
                url: LOGIN_URL + "/token",
                type: "GET",
                contentType: "application/json;charset=UTF-8",
                headers: {
                    "authorization": userToken
                },
                success: function (res) {
                    if (res.status == "success") {
                        if (res.data != null) {
                            $(".top_menu .user").css("display", "block");
                            $(".top_menu .popupLogin").css("display", "none");
                            $(".user_nickname").text(res.data);
                        } else {
                            $(".top_menu .user").css("display", "none");
                            $(".top_menu .popupLogin").css("display", "block");
                        }
                    }
                }
            })
        }
        $('.popupLogin').popup({
        });
        $(".form-horizontal .create_account").click(function () {
            $(".form-horizontal").html("<div class=\"heading\">注  册</div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <i class=\"fa fa-user\"></i><input required name=\"register[email]\" type=\"email\"\n" +
                "                                                             class=\"form-control\" placeholder=\"Email\"\n" +
                "                                                             id=\"email\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group\">\n" +
                "                            <i class=\"fa fa-lock\"></i><input required name=\"register[nickname]\" type=\"text\"\n" +
                "                                                             class=\"form-control\" placeholder=\"Nickname\"\n" +
                "                                                             id=\"nickname\">\n" +
                "                        </div>" +
                "                        <div class=\"form-group\">\n" +
                "                            <i class=\"fa fa-lock\"></i><input required name=\"register[password]\" type=\"password\"\n" +
                "                                                             class=\"form-control\" placeholder=\"Password\"\n" +
                "                                                             id=\"password\">\n" +
                "                        </div>" +
                "                        <button class=\"btn btn-default submit\"><i class=\"fa fa-arrow-right\"></i></button>\n"
            );
            
        });
        
        $(".form-horizontal .submit").click(function () {
            console.log("123");
            var heading = $(".form-horizontal .heading").text();
            heading = heading.toString();
            console.log(heading);
            var url = LOGIN_URL;
            var email = null;
            var password = null;
            var nickname = null;
            if (heading === "登  录") {
                email = $("#email").val();
                password = $("#password").val();
                url += "/login";
            } else if (heading === "注  册") {
                console.log("2");
                url += "/register";
                console.log("email:" + email);
                console.log("nickname:" + nickname);
                console.log("password:" + password);
            } else {return;}

            console.log("email:" + email);
            console.log("nickname:" + nickname);
            console.log("password:" + password);
            $.ajax({
                url: url,
                type: "POST",
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                data: {
                    "email": email,
                    "nickname": nickname,
                    "password": password
                },
                success: function (res) {
                    if (res.status == "success") {
                        userToken = res.data;
                        $.session.set('token', res.data);
                        console.log(userToken);
                        alert("登录成功！", null, function () {
                            $('.popupLogin').popup().close();
                            window.location.reload();
                        }, {type: 'success', confirmButtonText: 'OK'});
                    } else {
                        alert(res.data.errorMsg, null, function () {
                            $('#login').modal();
                        }, {type: 'error', confirmButtonText: 'OK'});
                    }
                }
            })
        })

        $(".user_nickname").click(function () {
            $.session.remove("token");
            window.location.reload();
        })
    }
    
    function order() {
        $(".checkout .checkout__order .checkout__option").click(function () {
            var goods = new Array();
            var summary = $(this).parent();
            $(summary + " tbody tr").each(function() {
                let id = $(this).find(".book_id").val();
                let num = $(this).find(".goods_num").text();
                let obj = new Object();
                obj.goodId = id;
                obj.goodNum = num;
                goods.add(obj);
            });
            $.ajax({
                url: BASE_URL + "/order/order",
                type: "POST",
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                headers: {
                    "authorization": userToken
                },
                data: {
                    "list": goods
                },
                success: function (res) {
                    if (res.status == "success") {

                    }
                }
            })
        })
    }
    
});