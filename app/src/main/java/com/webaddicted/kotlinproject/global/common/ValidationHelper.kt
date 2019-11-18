package com.webaddicted.kotlinproject.global.common

import android.content.Context
import android.graphics.Color
import android.text.InputFilter
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.webaddicted.kotlinproject.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class ValidationHelper {
    companion object {
        private val mAlpha = 0.4f

        val blockedSpecialCharacterFilter: InputFilter
            get() {
                val blockCharacterSet = "~#^|$%&*!@+_-1234567890"
                return InputFilter { source, start, end, dest, dstart, dend ->
                    if (source != null && blockCharacterSet.contains("" + source)) {
                        ""
                    } else null
                }

            }


        fun isChecked(@Nullable checkbox: CheckBox, @Nullable msg: String): Boolean {
            if (!checkbox.isChecked) {
                showToast(checkbox.context, msg)
                return false
            }
            return true
        }

        enum class ALERT_TYPE {
            TOAST, SNACK_BAR
        }

        /**
         * This method returns true if a edit text is blank ,false otherwise
         *
         * @param targetEditText source edit text
         * @param msg            message to be shown in snackbar
         * @return
         */
        fun isBlank(@NonNull targetEditText: TextView, msg: String): Boolean {
            val source = targetEditText.text.toString().trim { it <= ' ' }
            if (source.isEmpty()) {
                showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg)
                return true
            }
            return false
        }

        fun isBlank(@NonNull targetEditText: TextView): Boolean {
            return targetEditText.text.toString().trim { it <= ' ' }.isEmpty()
        }

        fun validatePasswordSameFields(
            password: EditText,
            confPassword: EditText,
            message: String,
            showToast: Boolean
        ): Boolean {
            if (password.text.toString() == confPassword.text.toString()) {
                return true
            } else {
                if (showToast)
                    showToast(password.context, message)
                else
                    showAlert(password, ALERT_TYPE.SNACK_BAR, message)
                return false
            }
        }

        fun isBlank(@NonNull textView: TextView, msg: String, showToast: Boolean): Boolean {
            val source = textView.text.toString().trim { it <= ' ' }
            if (source.isEmpty() && showToast) {
                showToast(textView.context, msg)
                return true
            }
            return false
        }

        fun hasMinimumLength(
            editText: EditText,
            length: Int,
            message: String,
            showToast: Boolean
        ): Boolean {
            if (!hasMinimumLength(editText.text.toString().trim { it <= ' ' }, length)) {
                if (showToast)
                    showToast(editText.context, message)
                else
                    showAlert(editText, ALERT_TYPE.SNACK_BAR, message)
                return false
            }
            return true

        }


        /**
         * @param v view for check mAlpha
         * @return mAlpha status
         */
        fun isAlphaEnable(v: View?): Boolean {
            return if (v != null) {
                if (v.alpha < 1)
                    true
                else
                    false
            } else false
        }

        /**
         * @param v        view for set mAlpha
         * @param setAlpha
         */
        fun setAlpha(v: View?, setAlpha: Boolean) {
            if (v != null) {
                if (setAlpha)
                    v.alpha = mAlpha
                else
                    v.alpha = 1f
            }
        }

        /**
         * This method returns true if a edit text contains valid email ,false otherwise
         *
         * @param targetEditText source edit text
         * @param msg            message to be shown in snackbar
         * @return
         */
        fun isEmailValid(@NonNull targetEditText: EditText, msg: String): Boolean {
            val source = targetEditText.text.toString().trim { it <= ' ' }
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val p = Pattern.compile(
                expression,
                Pattern.CASE_INSENSITIVE
            ) // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
            val m = p.matcher(source)
            if (m.matches() && source.trim { it <= ' ' }.length > 0) {
                return true
            }
            showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg)
            return false
        }

        fun isEmailValid(
            @NonNull targetEditText: EditText, msg: String,
            showToast: Boolean
        ): Boolean {
            val source = targetEditText.text.toString().trim { it <= ' ' }
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val p = Pattern.compile(
                expression,
                Pattern.CASE_INSENSITIVE
            ) // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
            val m = p.matcher(source)
            if (m.matches() && source.trim { it <= ' ' }.length > 0) {
                return true
            }
            if (showToast)
                showAlert(targetEditText, ALERT_TYPE.TOAST, msg)
            return false
        }

        fun isEmailValid(@NonNull targetEditText: EditText): Boolean {
            val source = targetEditText.text.toString().trim { it <= ' ' }
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val p = Pattern.compile(
                expression,
                Pattern.CASE_INSENSITIVE
            ) // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
            val m = p.matcher(source)
            return if (m.matches() && source.trim { it <= ' ' }.length > 0) {
                true
            } else false

        }

        /**
         * This method returns true if a edit text contains any digit in it ,false otherwise
         *
         * @param targetEditText source edit text
         * @param msg            message to be shown in snackbar
         * @return
         */
        fun isContainDigit(
            @NonNull targetEditText: EditText, alertType: ALERT_TYPE,
            msg: String,
            msgType: Boolean
        ): Boolean {
            val pattern = ".*\\d.*"
            val source = targetEditText.text.toString().trim { it <= ' ' }
            if (source.matches(pattern.toRegex())) {
                if (msgType) {
                    showAlert(targetEditText, alertType, msg)
                }
                return true
            } else {
                if (!msgType) {
                    showAlert(targetEditText, alertType, msg)
                }
                return false
            }
        }

        fun passwordCheck(string: String): Boolean {
            val pattern = ".*\\d.*"
            val atleastOneAlpha = string.matches(".*[a-zA-Z]+.*".toRegex())
            val source = string.trim { it <= ' ' }
            if (!source.matches(pattern.toRegex()) || string.length < 8 || !atleastOneAlpha) {
            } else
                return true
            return false
        }

        fun containOnlyDigit(@NonNull targetEditText: EditText, msg: String): Boolean {
            val pattern = "\\d+"
            val source = targetEditText.text.toString().trim { it <= ' ' }
            if (source.matches(pattern.toRegex())) {
                return true
            } else {
                showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg)
                return false
            }
        }


        fun isEqual(
            @NonNull sourceEditText: EditText, @NonNull destinationEditText: EditText, msg: String,
            msgType: Boolean
        ): Boolean {
            return isEqual(sourceEditText, destinationEditText, ALERT_TYPE.SNACK_BAR, msg, msgType)

        }

        fun isEqual(
            @NonNull sourceEditText: EditText, @NonNull destinationEditText: EditText, alertType: ALERT_TYPE,
            msg: String,
            msgType: Boolean
        ): Boolean {

            val source = sourceEditText.text.toString().trim { it <= ' ' }
            val destination = destinationEditText.text.toString().trim { it <= ' ' }
            if (source.equals(destination, ignoreCase = true)) {
                if (msgType) {
                    showAlert(destinationEditText, alertType, msg)
                }
                return true
            } else {
                if (!msgType) {
                    showAlert(destinationEditText, alertType, msg)
                }
                return false
            }

        }

        fun isEqual(@NonNull sourceEditText: EditText, @NonNull destinationEditText: EditText, msg: String): Boolean {

            val source = sourceEditText.text.toString().trim { it <= ' ' }
            val destination = destinationEditText.text.toString().trim { it <= ' ' }
            if (source.equals(destination, ignoreCase = true)) {
                return true
            } else {
                showToast(destinationEditText.context, msg)
                return false
            }

        }

        fun isSame(
            @NonNull sourceEditText: TextView, destinationString: String,
            msg: String
        ): Boolean {

            val source = sourceEditText.text.toString().trim { it <= ' ' }
            if (source.equals(destinationString, ignoreCase = true)) {
                showToast(sourceEditText.context, msg)
                return true
            }
            return false

        }

        fun showToast(context: Context, msg: String) {
            Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).show()
        }

        private fun showAlert(context: Context, msg: String) {

        }

        private fun showAlert(targetEditText: TextView, alertType: ALERT_TYPE, msg: String) {
            //View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
            targetEditText.requestFocus()
            if (alertType == ALERT_TYPE.TOAST) {
                showToast(targetEditText.context, msg)
            } else if (alertType == ALERT_TYPE.SNACK_BAR) {
                showSnackBar(targetEditText, msg)
            }

        }


        fun showSnackBar(parentLayout: View, msg: String) {
            val snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT)
            snackBar.setActionTextColor(Color.WHITE)
            val view = snackBar.getView()
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(Color.WHITE)
            snackBar.show()

        }

        fun hasMinimumLength(source: String, length: Int): Boolean {
            return if (source.trim { it <= ' ' }.length >= length) true else false
        }

        fun hasMinimumLength(editText: EditText, length: Int, message: String): Boolean {
            if (!hasMinimumLength(editText.text.toString().trim { it <= ' ' }, length)) {
                showAlert(editText, ALERT_TYPE.SNACK_BAR, message)
                return false
            }
            return true

        }

        fun isValidName(textView: TextView, message: String): Boolean {
            val targetString = textView.text.toString().trim { it <= ' ' }
            val regx = "^[\\p{L} .'-]+$"
            if (Pattern.matches(regx, targetString)) {
                return true
            }
            showAlert(textView, ALERT_TYPE.SNACK_BAR, message)
            return false
        }

        fun hasMinimumwords(
            editText: EditText,
            alertType: ALERT_TYPE,
            length: Int,
            message: String
        ): Boolean {
            if (editText.text.toString().trim { it <= ' ' }.length >= length) {
                showAlert(editText, alertType, message)
                return false
            } else {
                return true
            }
        }

        fun isValidURL(mFeedEditText: EditText, alertType: ALERT_TYPE, msg: String): Boolean {

            val url = mFeedEditText.text.toString().toLowerCase()
            if (Patterns.WEB_URL.matcher(url).matches()) {
                return true
            } else {
                showAlert(mFeedEditText, alertType, msg)
                return false
            }
        }

        fun isValidExpDate(expDate: String): Boolean {
            val sdf = SimpleDateFormat("MM/yy", Locale.getDefault())
            var strDate: Date? = null
            try {
                strDate = sdf.parse(expDate)
            } catch (e: ParseException) {
                return false
            }

            val validDate = GregorianCalendar()
            validDate.time = strDate
            return Calendar.getInstance().before(validDate)
        }

        fun isValidExpDate(expDateEt: EditText, msg: String): Boolean {
            val expDate = expDateEt.text.toString()
            if (!isValidExpDate(expDate)) {
                showSnackBar(expDateEt, msg)
                return false
            }
            return true
        }

        fun isValidPassword(
            layoutPassword: TextInputLayout,
            password: String,
            errorMsg: String
        ): Boolean {
            val UpperCasePatten = Pattern.compile("[A-Z ]")
            val lowerCasePatten = Pattern.compile("[a-z ]")
            val digitCasePatten = Pattern.compile("[0-9 ]")
            if (!UpperCasePatten.matcher(password).find()) {
                layoutPassword.setError(layoutPassword.getContext().getResources().getString(R.string.error_uppercase_pattern))
                return false
            } else if (!lowerCasePatten.matcher(password).find()) {
                layoutPassword.setError(layoutPassword.getContext().getResources().getString(R.string.error_lowercase_pattern))
                return false
            } else if (!digitCasePatten.matcher(password).find()) {
                layoutPassword.setError(layoutPassword.getContext().getResources().getString(R.string.error_digit_pattern))
                return false
            } else if (password.length < 8) {
                layoutPassword.setError(errorMsg)
                return false
            }
            layoutPassword.setError(null)
            return true

        }


        /**
         * check username validation
         *
         * @param edtName     first name widget
         * @param wrapperName
         * @return name validation status
         */
        fun validateName(edtName: TextInputEditText, wrapperName: TextInputLayout): Boolean {
            if (ValidationHelper.isBlank(edtName)) {
                wrapperName.setError(edtName.getContext().getResources().getString(R.string.enter_first_name))
            } else {
                wrapperName.setError(null)
                return true
            }
            return false
        }


        /**
         * check email id validation
         *
         * @param wrapperEmailId
         * @param edtEmailId     email id widget
         * @return email id validation status
         */
        fun validateEmail(edtEmailId: TextInputEditText, wrapperEmailId: TextInputLayout): Boolean {
            if (ValidationHelper.isBlank(edtEmailId)) {
                wrapperEmailId.setError(edtEmailId.getContext().getResources().getString(R.string.enter_your_email))
            } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmailId.getText().toString()).matches()) {
                wrapperEmailId.setError(edtEmailId.getContext().getResources().getString(R.string.error_enter_valid_email))
            } else {
                wrapperEmailId.setError(null)
                return true
            }
            return false
        }

        /**
         * check email id validation
         *
         * @param edtEmailId email id widget
         * @return email id validation status
         */
        fun validateFatherEmail(
            edtEmailId: TextInputEditText,
            inputLayout: TextInputLayout,
            childEmail: String
        ): Boolean {
            if (ValidationHelper.isBlank(edtEmailId)) {
                inputLayout.setError(edtEmailId.getContext().getResources().getString(R.string.enter_your_email))
            } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmailId.getText().toString()).matches()) {
                inputLayout.setError(edtEmailId.getContext().getResources().getString(R.string.error_enter_valid_email))
            } else if (edtEmailId.getText().toString().trim().equals(childEmail.trim { it <= ' ' })) {
                inputLayout.setError(edtEmailId.getContext().getResources().getString(R.string.error_email_must_be_different))
            } else {
                inputLayout.setError(null)
                return true
            }
            return false
        }

        /**
         * check dob validation
         *
         * @param edtDob     dob widget
         * @param wrapperDob
         * @return dob validation status
         */
        fun validateDob(edtDob: TextInputEditText, wrapperDob: TextInputLayout): Boolean {
            if (ValidationHelper.isBlank(edtDob)) {
                wrapperDob.setError(edtDob.getContext().getResources().getString(R.string.enter_dob))
            } else {
                wrapperDob.setError(null)
                return true
            }
            return false
        }

        /**
         * check country validation
         *
         * @param edtCountry     country widget
         * @param wrapperCountry
         * @return country validation status
         */
        fun validateCountry(
            edtCountry: TextInputEditText,
            wrapperCountry: TextInputLayout
        ): Boolean {
            if (ValidationHelper.isBlank(edtCountry)) {
                wrapperCountry.setError(edtCountry.getContext().getResources().getString(R.string.enter_country))
            } else {
                wrapperCountry.setError(null)
                return true
            }
            return false
        }

        /**
         * check password validation
         *
         * @param wrapperPsw
         * @param edtPassword password widget
         * @return password validation status
         */
        fun validatePwd(edtPassword: TextInputEditText, wrapperPsw: TextInputLayout): Boolean {
            if (isBlank(edtPassword)) {
                wrapperPsw.setError(edtPassword.getContext().getResources().getString(R.string.enter_passowrd))
            } else if (!isValidPassword(
                    wrapperPsw,
                    edtPassword.getText().toString(),
                    edtPassword.getContext().getResources().getString(R.string.password_length)
                )
            ) {
            } else {
                wrapperPsw.setError(null)
                return true
            }
            return false
        }

        /**
         * check confirm password validation
         *
         * @param confirmPassword password widget
         * @param wrapperConPsw
         * @param password
         * @return confirm password validation status
         */
        fun validateConfirmPwd(
            confirmPassword: TextInputEditText,
            wrapperConPsw: TextInputLayout,
            password: String
        ): Boolean {
            if (isBlank(confirmPassword)) {
                wrapperConPsw.setError(confirmPassword.getContext().getResources().getString(R.string.enter_passowrd))
            } else if (!isValidPassword(
                    wrapperConPsw,
                    confirmPassword.getText().toString(),
                    confirmPassword.getContext().getResources().getString(R.string.confirm_pwd_length)
                )
            ) {
            } else if (!confirmPassword.getText().toString().equals(password)) {
                wrapperConPsw.setError(confirmPassword.getContext().getResources().getString(R.string.error_passowrd_not_match))
            } else {
                wrapperConPsw.setError(null)
                return true
            }
            return false
        }

        /**
         * check edit profile validation
         *
         * @param textInput     widget
         * @param wrapperNewPwd
         * @return change password validation status
         */
        fun validateConfirmChangePwd(
            textInput: TextInputEditText,
            wrapperNewPwd: TextInputLayout,
            oldPassword: String
        ): Boolean {
            if (isBlank(textInput)) {
                wrapperNewPwd.setError(textInput.getContext().getResources().getString(R.string.enter_passowrd))
            } else if (!isValidPassword(
                    wrapperNewPwd,
                    textInput.getText().toString(),
                    textInput.getContext().getResources().getString(R.string.confirm_pwd_length)
                )
            ) {
            } else if (textInput.getText().toString().trim().equals(oldPassword.trim { it <= ' ' })) {
                wrapperNewPwd.setError(textInput.getContext().getResources().getString(R.string.error_password_different))
            } else {
                wrapperNewPwd.setError(null)
                return true
            }
            return false
        }

        fun validateReferral(
            edtReferralCode: TextInputEditText,
            wrapperReferralCode: TextInputLayout
        ): Boolean {
            if (ValidationHelper.isBlank(edtReferralCode)) {
                wrapperReferralCode.setError(edtReferralCode.getContext().getResources().getString(R.string.error_referral_code))
            } else {
                wrapperReferralCode.setError(null)
                return true
            }
            return false
        }

        fun validateBlank(
            textInput: TextInputEditText,
            wrapper: TextInputLayout,
            enter_subject: String
        ): Boolean {
            if (ValidationHelper.isBlank(textInput)) {
                wrapper.setError(enter_subject)
            } else {
                wrapper.setError(null)
                return true
            }
            return false
        }

        fun validateMobileNo(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
            if (isBlank(textInput)) {
                    wrapper.setError(textInput.getContext().getResources().getString(R.string.enter_mobile_no))
            } else if (textInput.text.toString().trim().length != 10) {
                    wrapper.setError(textInput.getContext().getResources().getString(R.string.enter_valid_mobile_no))
            } else {
                    wrapper.setError(null)
                return true
            }
            return false
        }
        fun validateMobileNo(textInput: TextInputEditText): Boolean {
            if (isBlank(textInput)) {
                textInput.setError(textInput.getContext().getResources().getString(R.string.enter_mobile_no))
            } else if (textInput.text.toString().trim().length != 10) {
                textInput.setError(textInput.getContext().getResources().getString(R.string.enter_valid_mobile_no))
            } else {
                textInput.setError(null)
                return true
            }
            return false
        }
        fun validateCode(textInput: TextInputEditText, wrapperCode: TextInputLayout): Boolean {
            if (isBlank(textInput)) {
                 wrapperCode.setError(textInput.getContext().getResources().getString(R.string.error_valid_code))
            } else if (!(textInput.getText().toString().length === 6)) {
                wrapperCode.setError(textInput.getContext().getResources().getString(R.string.error_six_digit_code))
            } else {
                wrapperCode.setError(null)
                return true
            }
            return false
        }

        fun validateCode(textInput: TextInputEditText): Boolean {
            if (isBlank(textInput)) {
                textInput.setError(textInput.getContext().getResources().getString(R.string.error_valid_code))
            } else if (!(textInput.getText().toString().length === 6)) {
                textInput.setError(textInput.getContext().getResources().getString(R.string.error_six_digit_code))
            } else {
                textInput.setError(null)
                return true
            }
            return false
        }
    }
}
