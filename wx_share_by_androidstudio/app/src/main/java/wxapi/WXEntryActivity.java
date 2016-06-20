package wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.andli.sharedemo.Constants;
import com.andli.sharedemo.MainActivity;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author andli
 * @date 2016年6月15日 下午2:33:00
 * @annotation 回调处理页面
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //如果分享的时候，该界面没有开启，那么微信开始这个activity时，会调用onCreate，所以这里要处理微信的返回结果
        MainActivity.api.handleIntent(getIntent(), this);
	}

	// 如果分享的时候，该已经开启，那么微信开始这个activity时，会调用onNewIntent，所以这里要处理微信的返回结果
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
        setIntent(intent);
        MainActivity.api.handleIntent(getIntent(), this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq arg0) {
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	// 比如：在微信完成文本分享操作后，回调第三方APP
	@Override
	public void onResp(BaseResp resp) {
		String result;
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功!";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "发送取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "发送拒绝";
			break;
		default:
			result = "发送返回";
			break;
		}

		Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		// 关闭页面
		this.finish();
	}
}
