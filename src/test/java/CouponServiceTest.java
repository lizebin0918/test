import java.io.ByteArrayOutputStream;

import org.approvaltests.ApprovalUtilities;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

/**
 * <dependency>
 *             <groupId>com.approvaltests</groupId>
 *             <artifactId>approvaltests</artifactId>
 *             <version>18.5.0</version>
 *         </dependency>
 *         <br/>
 * Created on : 2023-01-08 10:09
 * @author mac
 */
class CouponServiceTest {

	private CouponService couponService = new CouponService();

	@Test
	void should_be_true_when_store_no_and_store_user_no_is_empty() {

		ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();

		CouponService.CmsCustomerCoupon coupon = new CouponService.CmsCustomerCoupon();
		couponService.matchUseStore(coupon, "01");

		Approvals.verify(SystemOutUtils.convert(output));

	}

	@Test
	void should_be_true_when_store_no_is_used() {
		ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();

		CouponService.CmsCustomerCoupon coupon = new CouponService.CmsCustomerCoupon();
		coupon.setUseStoreNos("01");
		couponService.matchUseStore(coupon, "01");

		Approvals.verify(SystemOutUtils.convert(output));
	}

	@Test
	void should_be_false_when_store_no_is_not_used() {
		ByteArrayOutputStream output = new ApprovalUtilities().writeSystemOutToStringBuffer();

		CouponService.CmsCustomerCoupon coupon = new CouponService.CmsCustomerCoupon();
		coupon.setUseStoreNos("01");
		couponService.matchUseStore(coupon, "100");

		Approvals.verify(SystemOutUtils.convert(output));
	}

}
