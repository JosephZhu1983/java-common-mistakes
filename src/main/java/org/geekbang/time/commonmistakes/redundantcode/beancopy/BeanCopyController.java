package org.geekbang.time.commonmistakes.redundantcode.beancopy;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("beancopy")
public class BeanCopyController {

    @GetMapping("wrong")
    public ComplicatedOrderDO wrong() {
        ComplicatedOrderDTO orderDTO = new ComplicatedOrderDTO();
        ComplicatedOrderDO orderDO = new ComplicatedOrderDO();
        orderDO.setAcceptDate(orderDTO.getAcceptDate());
        orderDO.setAddress(orderDTO.getAddress());
        orderDO.setAddressId(orderDTO.getAddressId());
        orderDO.setCancelable(orderDTO.isCancelable());
        orderDO.setCommentable(orderDTO.isComplainable()); //属性错误
        orderDO.setComplainable(orderDTO.isCommentable()); //属性错误
        orderDO.setCancelable(orderDTO.isCancelable());
        orderDO.setCouponAmount(orderDTO.getCouponAmount());
        orderDO.setCouponId(orderDTO.getCouponId());
        orderDO.setCreateDate(orderDTO.getCreateDate());
        orderDO.setDirectCancelable(orderDTO.isDirectCancelable());
        orderDO.setDeliverDate(orderDTO.getDeliverDate());
        orderDO.setDeliverGroup(orderDTO.getDeliverGroup());
        orderDO.setDeliverGroupOrderStatus(orderDTO.getDeliverGroupOrderStatus());
        orderDO.setDeliverMethod(orderDTO.getDeliverMethod());
        orderDO.setDeliverPrice(orderDTO.getDeliverPrice());
        orderDO.setDeliveryManId(orderDTO.getDeliveryManId());
        orderDO.setDeliveryManMobile(orderDO.getDeliveryManMobile()); //对象错误
        orderDO.setDeliveryManName(orderDTO.getDeliveryManName());
        orderDO.setDistance(orderDTO.getDistance());
        orderDO.setExpectDate(orderDTO.getExpectDate());
        orderDO.setFirstDeal(orderDTO.isFirstDeal());
        orderDO.setHasPaid(orderDTO.isHasPaid());
        orderDO.setHeadPic(orderDTO.getHeadPic());
        orderDO.setLongitude(orderDTO.getLongitude());
        orderDO.setLatitude(orderDTO.getLongitude()); //属性赋值错误
        orderDO.setMerchantAddress(orderDTO.getMerchantAddress());
        orderDO.setMerchantHeadPic(orderDTO.getMerchantHeadPic());
        orderDO.setMerchantId(orderDTO.getMerchantId());
        orderDO.setMerchantAddress(orderDTO.getMerchantAddress());
        orderDO.setMerchantName(orderDTO.getMerchantName());
        orderDO.setMerchantPhone(orderDTO.getMerchantPhone());
        orderDO.setOrderNo(orderDTO.getOrderNo());
        orderDO.setOutDate(orderDTO.getOutDate());
        orderDO.setPayable(orderDTO.isPayable());
        orderDO.setPaymentAmount(orderDTO.getPaymentAmount());
        orderDO.setPaymentDate(orderDTO.getPaymentDate());
        orderDO.setPaymentMethod(orderDTO.getPaymentMethod());
        orderDO.setPaymentTimeLimit(orderDTO.getPaymentTimeLimit());
        orderDO.setPhone(orderDTO.getPhone());
        orderDO.setRefundable(orderDTO.isRefundable());
        orderDO.setRemark(orderDTO.getRemark());
        orderDO.setStatus(orderDTO.getStatus());
        orderDO.setTotalQuantity(orderDTO.getTotalQuantity());
        orderDO.setUpdateTime(orderDTO.getUpdateTime());
        orderDO.setName(orderDTO.getName());
        orderDO.setUid(orderDTO.getUid());
        return orderDO;
    }

    @GetMapping("right")
    public ComplicatedOrderDO right() {
        ComplicatedOrderDTO orderDTO = new ComplicatedOrderDTO();
        ComplicatedOrderDO orderDO = new ComplicatedOrderDO();
        BeanUtils.copyProperties(orderDTO, orderDO, "id");
        return orderDO;
    }

    @Data
    class ComplicatedOrderDO {
        private Long id;
        private String orderNo;
        private Integer totalQuantity;
        private Date createDate;
        private Date deliverDate;
        private Date outDate;
        private Date expectDate;
        private Integer paymentMethod;
        private Date acceptDate;
        private Long uid;
        private String nickName;
        private String headPic;
        private String phone;
        private Long merchantId;
        private String merchantName;
        private String merchantHeadPic;
        private String merchantPhone;
        private String merchantAddress;
        private String remark;
        private BigDecimal deliverPrice;
        private Integer deliverMethod;
        private Integer paymentTimeLimit;
        private String address;
        private Long addressId;
        private String name;
        private Integer status;
        private boolean cancelable;
        private boolean payable;
        private boolean hasPaid;
        private boolean complainable;
        private boolean commentable;
        private boolean refundable;
        private Double distance;
        private Long couponId;
        private BigDecimal couponAmount;
        private boolean firstDeal;
        private Double longitude;
        private Double latitude;
        private Integer deliverGroup;
        private boolean directCancelable;
        private BigDecimal paymentAmount;
        private Date paymentDate;
        private Integer deliverGroupOrderStatus;
        private Integer deliveryManId;
        private String deliveryManName;
        private String deliveryManMobile;
        private Date updateTime;
    }

    @Data
    class ComplicatedOrderDTO {
        private Long id = 1L;
        private String orderNo = "orderNo";
        private Integer totalQuantity = 1;
        private Date createDate = new Date();
        private Date deliverDate = new Date();
        private Date outDate = new Date();
        private Date expectDate = new Date();
        private Integer paymentMethod = 1;
        private Date acceptDate = new Date();
        private Long uid = 1L;
        private String nickName = "nickName";
        private String headPic = "headPic";
        private String phone = "136";
        private Long merchantId = 1L;
        private String merchantName = "merchantName";
        private String merchantHeadPic = "merchantHeadPic";
        private String merchantPhone = "merchantPhone";
        private String merchantAddress = "merchantAddress";
        private String remark = "remark";
        private BigDecimal deliverPrice = BigDecimal.ONE;
        private Integer deliverMethod = 1;
        private Integer paymentTimeLimit = 1;
        private String address = "address";
        private Long addressId = 1L;
        private String name = "name";
        private Integer status = 1;
        private boolean cancelable = true;
        private boolean payable = true;
        private boolean hasPaid = false;
        private boolean complainable = false;
        private boolean commentable = true;
        private boolean refundable = true;
        private Double distance = 1d;
        private Long couponId = 1L;
        private BigDecimal couponAmount = BigDecimal.ONE;
        private boolean firstDeal = true;
        private Double longitude = 1.234d;
        private Double latitude = 2.345d;
        private Integer deliverGroup = 1;
        private boolean directCancelable = true;
        private BigDecimal paymentAmount = BigDecimal.ONE;
        private Date paymentDate = new Date();
        private Integer deliverGroupOrderStatus = 1;
        private Integer deliveryManId = 1;
        private String deliveryManName = "deliveryManName";
        private String deliveryManMobile = "deliveryManMobile";
        private Date updateTime = new Date();
    }
}
