export const useFormValidators = () => {
  const validateRequired = (value: string, fieldName: string) =>
    value ? '' : `${fieldName}을(를) 입력해주세요.`

  const validatePasswordMatch = (password: string, confirmPassword: string) =>
    password === confirmPassword ? '' : '비밀번호가 일치하지 않습니다.'

  const validateAgreement = (agreed: boolean) =>
    agreed ? '' : '개인정보 수집 및 이용에 동의해주세요.'

  return {
    validateRequired,
    validatePasswordMatch,
    validateAgreement
  }
}
