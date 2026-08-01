package com.banking_app.banking_app.dtos;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedTransactionsResponse {
    List<TransactionResponse> transactions;
    Long totalElements;
    int totalPages;
}
