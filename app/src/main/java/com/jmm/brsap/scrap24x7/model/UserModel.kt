package com.jmm.brsap.scrap24x7.model

data class UserModel(
    val ActiveAttemptCount: Int? = null,
    val BlockedBy: Int? = null,
    val BlockedOn: String? = null,
    val BlockedStatus: Boolean? = null,
    val Comment: String? = null,
    val LoginID: String? = null,
    val IncorrectAttemptCount: Int? = null,
    val LastIncorrectAttemptOn: String? = null,
    val ModifiedBy: Int? = null,
    val ModifiedOn: String? = null,
    val Password: String? = null,
    val SysGenPwd: String? = null,
    val SysPwdFlag: Boolean? = null,
    val UserID: Int? = null,
    val UserName: String? = null,
    val UserRoleID: Int? = null
){
    override fun toString(): String {
        return super.toString()
    }
}