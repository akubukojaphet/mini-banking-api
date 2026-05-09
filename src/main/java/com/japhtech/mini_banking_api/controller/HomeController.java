package com.japhtech.mini_banking_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>JaphTech Bank API</title>
                    <style>
                        body {
                            margin: 0;
                            font-family: Arial, sans-serif;
                            background: linear-gradient(135deg, #07111f, #0b2d4d);
                            color: white;
                        }

                        .container {
                            max-width: 1100px;
                            margin: auto;
                            padding: 60px 25px;
                        }

                        .hero {
                            display: grid;
                            grid-template-columns: 1.2fr 0.8fr;
                            gap: 40px;
                            align-items: center;
                        }

                        .badge {
                            display: inline-block;
                            background: rgba(255, 255, 255, 0.12);
                            padding: 10px 18px;
                            border-radius: 30px;
                            margin-bottom: 20px;
                            color: #9ee6ff;
                        }

                        h1 {
                            font-size: 56px;
                            line-height: 1.1;
                            margin: 0 0 20px;
                        }

                        p {
                            font-size: 18px;
                            color: #d6e6f2;
                            line-height: 1.6;
                        }

                        .card {
                            background: rgba(255, 255, 255, 0.12);
                            border: 1px solid rgba(255, 255, 255, 0.18);
                            border-radius: 24px;
                            padding: 30px;
                            box-shadow: 0 25px 80px rgba(0, 0, 0, 0.35);
                        }

                        .balance {
                            font-size: 38px;
                            font-weight: bold;
                            margin: 20px 0;
                        }

                        .status {
                            color: #73ffb2;
                            font-weight: bold;
                        }

                        .endpoints {
                            margin-top: 55px;
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                            gap: 20px;
                        }

                        .endpoint {
                            background: white;
                            color: #111827;
                            padding: 22px;
                            border-radius: 18px;
                        }

                        .method {
                            font-size: 13px;
                            font-weight: bold;
                            color: #0057ff;
                        }

                        code {
                            display: block;
                            margin-top: 10px;
                            color: #0f172a;
                            font-weight: bold;
                        }

                        .footer {
                            margin-top: 50px;
                            color: #9fb8cc;
                            text-align: center;
                        }
                    </style>
                </head>

                <body>
                    <div class="container">
                        <div class="hero">
                            <div>
                                <div class="badge">Secure Banking Backend API</div>
                                <h1>JaphTech Mini Banking API</h1>
                                <p>
                                    A modern Spring Boot banking backend built with Java,
                                    PostgreSQL, JWT Authentication, Docker, and secure API architecture.
                                </p>
                                <p class="status">● System Online on Port 9090</p>
                            </div>

                            <div class="card">
                                <h2>Account Overview</h2>
                                <p>Demo Banking Infrastructure</p>
                                <div class="balance">$25,480.00</div>
                                <p>Protected with JWT Authentication</p>
                                <p>PostgreSQL Database Connected</p>
                                <p>Docker Ready Deployment</p>
                            </div>
                        </div>

                        <div class="endpoints">
                            <div class="endpoint">
                                <div class="method">POST</div>
                                <code>/api/auth/register</code>
                                <p>Create a new banking user account.</p>
                            </div>

                            <div class="endpoint">
                                <div class="method">POST</div>
                                <code>/api/auth/login</code>
                                <p>Login and receive a JWT token.</p>
                            </div>

                            <div class="endpoint">
                                <div class="method">GET</div>
                                <code>/api/account/balance</code>
                                <p>Check authenticated user balance.</p>
                            </div>

                            <div class="endpoint">
                                <div class="method">POST</div>
                                <code>/api/account/deposit</code>
                                <p>Deposit funds into an account.</p>
                            </div>

                            <div class="endpoint">
                                <div class="method">POST</div>
                                <code>/api/account/transfer</code>
                                <p>Transfer money between users.</p>
                            </div>

                            <div class="endpoint">
                                <div class="method">GET</div>
                                <code>/api/account/transactions</code>
                                <p>View transaction history.</p>
                            </div>
                        </div>

                        <div class="footer">
                            Built by JaphTech | Spring Boot • PostgreSQL • JWT • Docker
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}
