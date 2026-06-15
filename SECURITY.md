# Security Policy — SkillDNA AI

**Ravi Future Labs | Copyright (c) 2026 Ravikumar**

## Supported Versions

| Version | Supported |
|---------|-----------|
| 1.0.x   | ✅ Yes     |

## Reporting a Vulnerability

Please **do not** open a public GitHub issue for security vulnerabilities.

Report vulnerabilities privately via GitHub's Security Advisory feature:
➡️ https://github.com/ravigithubcse/skilldna-ai/security/advisories/new

We aim to acknowledge reports within **48 hours** and provide a fix within **14 days** for critical issues.

## Security Practices in This Codebase

- JWT HS256 signing with a minimum 32-character secret
- BCrypt password hashing (cost factor 12)
- No secrets committed to source control — all via environment variables
- Non-root Docker container execution
- Dependency vulnerability scanning via Trivy on every CI build
- HTTPS enforced in production
