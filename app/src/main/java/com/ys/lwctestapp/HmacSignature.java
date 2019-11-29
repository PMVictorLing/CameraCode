package com.ys.lwctestapp;
import com.google.gson.Gson;


/**
 * @author lwc
 * 2019/11/28
 * 描述：$des$
 */
public class HmacSignature {

        private Gson mGson = new Gson();

        private HmacSignature(){}

        public static void main(String[] args) {
            /*CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();

            form.setAmount(new BigDecimal("100"));
            form.setCurrency("JPY");
            form.setOrderId("merchant_order_id");

            ProductPackageForm productPackageForm = new ProductPackageForm();
            productPackageForm.setId("package_id");
            productPackageForm.setName("shop_name");
            productPackageForm.setAmount(new BigDecimal("100"));

            ProductForm productForm = new ProductForm();
            productForm.setId("product_id");
            productForm.setName("product_name");
            productForm.setImageUrl("");
            productForm.setQuantity(new BigDecimal("10"));
            productForm.setPrice(new BigDecimal("10"));
            productPackageForm.setProducts(Lists.newArrayList(productForm));

            form.setPackages(Lists.newArrayList(productPackageForm));
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setAppPackageName("");
            redirectUrls.setConfirmUrl("");
            form.setRedirectUrls(redirectUrls);

            String ChannelSecret = "3571c7f77b9dfaed5b8c09f2040d6f6d";
            String requestUri = "/v3/payments/request";
            String nonce = UUID.randomUUID().toString();
            //(ChannelSecret, ChannelSecret + requestUri + toJson(form) + nonce);
            String signature = Base64Utils.encode(HmacUtils.onHMACSHA256(ChannelSecret,ChannelSecret + requestUri + mGson.toJson(form) + nonce));*/
        }
}
