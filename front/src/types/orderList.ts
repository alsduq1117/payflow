export interface OrderList {
  id: number
  orderId : string | null
  buyerNickname: string | null
  productId: number | null
  productName: string | null
  thumbnailUrl: string | null
  fileUrl: string | null
  amount: number
  status: 'NOT_STARTED' | 'EXECUTING' | 'SUCCESS' | 'FAILURE' | 'UNKNOWN'
  method: string
  approvedAt?: string | null
}
