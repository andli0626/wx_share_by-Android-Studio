package com.andli.sharedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.andli.sharedemo.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

/**
 * author andli
 * create at 16/6/20 下午5:06
 * 集成官方SDK 实现简单文本分享
 **/

public class MainActivity extends Activity implements OnClickListener{
	// IWXAPI：第三方APP和微信通信的接口
	private IWXAPI api;
	// 文本分享按钮
	private Button shareTxTButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		// 向微信终端注册你的id
		api.registerApp(Constants.APP_ID);
		
		shareTxTButton = (Button)findViewById(R.id.shareTxTButton);
		shareTxTButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 文本分享
		case R.id.shareTxTButton:
			shareText();
			break;

		default:
			break;
		}

	}
	
	// 文本分享
	private void shareText() {
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = "微信文本分享测试";

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = "微信文本分享测试";
		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		// 分享或收藏的目标场景，通过修改scene场景值实现。
		// 发送到聊天界面 —— WXSceneSession
		// 发送到朋友圈 —— WXSceneTimeline
		// 添加到微信收藏 —— WXSceneFavorite
		req.scene = SendMessageToWX.Req.WXSceneTimeline;

		// 调用api接口发送数据到微信
		api.sendReq(req);
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()): type + System.currentTimeMillis();
	}

}
