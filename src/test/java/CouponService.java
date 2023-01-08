import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <br/>
 * Created on : 2023-01-08 10:09
 * @author mac
 */
public class CouponService {

	/**
	 * 判断指定门店是否可核销
	 *
	 * @param coupon
	 * @param storeNo
	 * @return
	 * @author dangshen
	 * @date 2021/5/20
	 */
	public boolean matchUseStore(CmsCustomerCoupon coupon, String storeNo) {

		populateUseStore(coupon);

		// lizebin 收集结果，用于断言
		SystemOutUtils.println(JSON.toJSONString(coupon));

		Set<String> useStoreNos = coupon.splitUseStoreNos();
		Set<String> excludeStoreNos = coupon.splitUseExcludeStoreNos();

		// 全部门店可用
		if (CollUtil.isEmpty(useStoreNos) && CollUtil.isEmpty(excludeStoreNos)) {
			// lizebin 收集结果，用于断言
			SystemOutUtils.println("true");
			return true;
		}

		// 指定门店可用
		if (CollUtil.isNotEmpty(useStoreNos)) {
			// coupon.getUseStores过滤了删除门店
			boolean r = (useStoreNos.contains(storeNo) &&
					coupon.getUseStores().stream().anyMatch(item -> item.getCode().equals(storeNo)));
			// lizebin 收集结果，用于断言
			SystemOutUtils.println(String.valueOf(r));
			return r;
		} else {
			// 指定门店不可用
			boolean r = !excludeStoreNos.contains(storeNo);
			// lizebin 收集结果，用于断言
			SystemOutUtils.println(String.valueOf(r));
			return r;
		}
	}

	private void populateUseStore(CmsCustomerCoupon coupon) {
		Set<String> useStoreNos = coupon.splitUseStoreNos();
		Set<String> useExcludeStoreNos = coupon.splitUseExcludeStoreNos();
		// 连锁所有门店
		List<CmsCustomerStore> stores = new ArrayList<>();
		stores.add(new CmsCustomerStore("01"));
		stores.add(new CmsCustomerStore("02"));
		stores.add(new CmsCustomerStore("03"));
		stores.add(new CmsCustomerStore("04"));
		stores.add(new CmsCustomerStore("05"));
		stores.add(new CmsCustomerStore("06"));
		stores.add(new CmsCustomerStore("07"));
		stores.add(new CmsCustomerStore("08"));
		stores.add(new CmsCustomerStore("09"));
		stores.add(new CmsCustomerStore("10"));
		coupon.setUseStores(stores.stream().filter(store -> useStoreNos.contains(store.getCode())).collect(Collectors.toList()));
		coupon.setUseExcludeStores(stores.stream().filter(store -> useExcludeStoreNos.contains(store.getCode())).collect(Collectors.toList()));
	}

	@Data
	public static class CmsCustomerCoupon {
		private String batchSequence;
		private String title;

		/**
		 * 指定核销门店
		 */
		private String useStoreNos;

		/**
		 * 指定不可核销门店
		 */
		private String useExcludeStoreNos;

		/**
		 * 指定核销门店
		 */
		private List<CmsCustomerStore> useStores;

		/**
		 * 指定可核销门店编号列表
		 *
		 * @param
		 * @return
		 * @author dangshen
		 * @date 2021/5/20
		 */
		public Set<String> splitUseStoreNos() {
			if (StringUtils.isNotBlank(useStoreNos)) {
				return Arrays.stream(useStoreNos.split(",")).collect(Collectors.toSet());
			}
			return Collections.emptySet();
		}

		/**
		 * 指定不可核销门店编号列表
		 *
		 * @param
		 * @return
		 * @author dangshen
		 * @date 2021/5/20
		 */
		public Set<String> splitUseExcludeStoreNos() {
			if (StringUtils.isNotBlank(useExcludeStoreNos)) {
				return Arrays.stream(useExcludeStoreNos.split(",")).collect(Collectors.toSet());
			}
			return Collections.emptySet();
		}

		/**
		 * 指定不可核销门店
		 */
		private List<CmsCustomerStore> useExcludeStores;
	}

	@Data
	public static class CmsCustomerStore {

		CmsCustomerStore(String code) {
			this.code = code;
		}

		private String code;
	}

}
