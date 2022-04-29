var careerism = {

	current_topic: 0,

	allQuestions : [],

	questionResult : [],

	testerId : -1,
	init: function() {

		this.bindEvent();

	},

	initTopic: function () {

		var self = this;

		$.ajax({
				url:"/analysis/mbquestion/selectByPage",
				contentType:"application/json",
				data:{page:1,limit:93},
				dataType:"json",//相应的数据类型：服务器给我的
				type:"get",
				async:false,//这样才能保证改变外部变量questions
				success:function (data) {
					//成功的响应函数  ：dataInfo名字随便写，代表的是服务器相应的内容
					if(data.code===0){
						//处理数据，使数据符合插件
						for (let i = 0; i < data.data.length; i++) {
							self.allQuestions[i] = JSON.stringify(data.data[i]);
						}
					}else{
						layer.msg(data.msg,{icon: 5});
					}
				}
			}
		)
		self.testerId = $("#userId").val();
		self.loadTopic(1);

	},

	loadTopic: function  (index) {



		var self = this;

		let question = JSON.parse(self.allQuestions[index-1]);

		var topic_number = index,

			topic_title = question.question,

			option_html = '';

		$('#curr_no').html(topic_number);

		$('#topic_title').html(topic_title);


		option_html += '<tr><td class="option_item" option_index="'+question.id+'" option_value="A" >' + question.optionA + '</td><tr>';
		option_html += '<tr><td class="option_item" option_index="'+question.id+'" option_value="B">' + question.optionB + '</td><tr>';
		// option_html += '<tr><td class="option_item" option_index="'+question.id+'" option_value="C">' + question.optionC + '</td><tr>';
		// option_html += '<tr><td class="option_item" option_index="'+question.id+'" option_value="D">' + question.optionD + '</td><tr>';
		$('#topic_option').html(option_html);

		self.current_topic++;

	},


	bindEvent: function () {

		var self = this;

		$('.topic_wrap').css('display','block');
		self.initTopic();
		$('.wrap').on('click','.option_item', function () {

			var _this = $(this);

			_this.addClass('select');
			//处理数据
			var res = {}
			res.userId = self.testerId;
			res.questionId = _this.attr('option_index');
			res.answer = _this.attr('option_value');
			self.questionResult.push(res);

			if(self.current_topic < self.allQuestions.length){

				$('.fade_wrap').fadeOut('normal',function () {

					self.loadTopic(self.current_topic+1);

					$(this).fadeIn('normal');

				});

			}else{
				//处理数据
				$.ajax({
					url: "/analysis/mbtiResult/insertBatch",
					data:JSON.stringify(self.questionResult),
					contentType: "application/json",
					type:"post",
					dataType: "json",
					success(data){
						if (data.code===0){
							// window.location.href = "toShowResult?uid="+self.testerId;
							mbtiResult

							window.location.href = "/analysis/toMbtiResult"
							console.log("插入成功")
							console.log(data)
						}else {
							window.alert("提交失败！");
						}
					}
				})

			}
		});

	}

}