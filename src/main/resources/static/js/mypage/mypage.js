$(".purchaseconfirm").click(function purchaseconfirm(){

                                let confirmed = confirm('구매확정하시면 되돌릴수없습니다.');
                                let csrfHeader = $('meta[name=_csrf_header]').attr('content');
                                let csrfToken = $('meta[name=_csrf]').attr('content');

                                        if(confirmed){
                                                let orderid = $(this).attr('data_orderid');
                                                let val = $(this).attr('data_value');

                                                if( !val ){
                                                    alert('구매확정은 배송완료단계에서만 할수있습니다.');
                                                    return;
                                                }

                                                let data = JSON.stringify({orderid: orderid});
                                                console.log(orderid);

                                                $.ajax({
                                               	url : "/mypage/purchaseconfirm",
                                               	type : "post",
                                               	data : data,
                                               	contentType: "application/json",
                                               	beforeSend : function(xhr){
                                                    xhr.setRequestHeader(csrfHeader,csrfToken);
                                                    xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
                                                },
                                               	success : function(result){
                                                    alert("구매확정 되었습니다.");
                                               	},
                                               	error : function(){
                                               		alert("잘못된 요청입니다. 다시 시도해주세요.");
                                               	    }
                                                })
                                        }

                            }
)

