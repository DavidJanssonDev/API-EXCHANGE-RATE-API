package org.example;

import org.httpApiClasses.ApiContext.ContextBodyObject;
import org.httpApiClasses.ApiContext.RequestValues.ComparisonAmountRequest;
import org.httpApiClasses.ApiContext.RequestValues.ComparisonRequest;
import org.httpApiClasses.ApiContext.RequestValues.EmptyRequest;
import org.httpApiClasses.ApiContext.RequestValues.StandardRequest;
import org.httpApiClasses.Enums.SupportedEndPointEnum;
import org.httpApiClasses.HttpsClientExtension;
import org.httpApiClasses.RetrunClasses.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class



Main {
    static void main() {

        HttpsClientExtension client = HttpsClientExtension.fromFile("API_SETTINGS");

        StandardApiResult standard = client.send(
                SupportedEndPointEnum.STANDARD,
                ContextBodyObject.convertToApiRequestValue(new StandardRequest("USD"))
        );
        System.out.println(standard);

        PairApiResult pair = client.send(
                SupportedEndPointEnum.COMPARISON,
                ContextBodyObject.convertToApiRequestValue(new ComparisonRequest("USD", "SEK"))
        );
        System.out.println(pair);

        PairAmountApiResult pairAmount = client.send(
                SupportedEndPointEnum.COMPARISON_AMOUNT,
                ContextBodyObject.convertToApiRequestValue(new ComparisonAmountRequest("USD", "SEK", 150))
        );
        System.out.println(pairAmount);

        CurrencyCodeApiResult codes = client.send(
                SupportedEndPointEnum.SUPPORTED_CURRENCY_CODE,
                ContextBodyObject.convertToApiRequestValue(new EmptyRequest())
        );
        System.out.println(codes);

        QuotaApiResult quota = client.send(
                SupportedEndPointEnum.QUOTA,
                ContextBodyObject.convertToApiRequestValue(new EmptyRequest())
        );
        System.out.println(quota);
    }

}
