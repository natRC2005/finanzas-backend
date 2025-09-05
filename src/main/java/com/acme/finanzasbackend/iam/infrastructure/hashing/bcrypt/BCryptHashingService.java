package com.acme.finanzasbackend.iam.infrastructure.hashing.bcrypt;

import com.acme.finanzasbackend.iam.application.internal.outboundingservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
