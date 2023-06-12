package com.lzb.mockito;

import io.github.classgraph.PackageInfo;

/**
 * 获取mock对象的参数，并做对应的处理<br/>
 * Created on : 2023-06-12 20:00
 * @author lizebin
 */
public class TestDoAnswer {

    /*@Test
    void should_test() {

        doAnswer(r -> {
            Runnable argument = r.getArgument(0);
            argument.run();
            return null;
        }).when(transactionUtils).runAfterCommit(any(Runnable.class));

        ArgumentCaptor<Long> packageId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> uid = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> logisticNumber = ArgumentCaptor.forClass(String.class);

        orderApplicationService.test();

        verify(packageApplicationService).cancelWaybill(packageId.capture(), logisticNumber.capture(), uid.capture());

        System.out.println(packageId.getAllValues());
        System.out.println(uid.getAllValues());
        System.out.println(logisticNumber.getValue());

    }*/

    /*public void test() {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setPackageId(1L);
        packageInfo.setLogisticsNumber("123456");
        transactionUtils.runAfterCommit(() -> {
            packageApplicationService.cancelWaybill(packageInfo.getPackageId(), packageInfo.getLogisticsNumber(), 1L);
        });
    }*/

}
