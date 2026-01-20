# Contributing to Kawalia

First off, 
thank you for considering contributing to Kawalia! It's people like you that make Kawalia such a great tool. 

**A Note on Maintenance:** This project is currently maintained by a single person. The maintainer will do their best to consider every idea and respond to issues and pull requests, but please be patient as responses may not be immediate.

## Project Status & Key Info

*   **Early Development:** Kawalia is in its early stages. We encourage discussions about architecture and core features. Please open an issue to start a conversation before making significant changes.
*   **Platform:** We currently use GitHub, but may migrate to another platform like GitLab in the future. This is not an urgent change, so you can contribute here with confidence.
*   **Release Cycle:** We follow a continuous release model. Features are released as they are completed and tested.
*   **Versioning:** Our versions follow the `YYYY.M.D+ProposalNumber` format (e.g., `2026.1.20+123`).

## Getting Started

Before you begin, please make sure you are proficient with **Java** and **Git**.

1.  **Fork the repository** on GitHub.
2.  **Clone your fork** to your local machine:
    ```bash
    git clone <your-fork-url>
    ```
3.  **Create a new branch** for your changes. It's important to branch off from `main`.
    ```bash
    git checkout -b <branch-name> main
    ```
    Your branch name should follow a clear convention. For example:
    *   `feat/new-feature-name`
    *   `bugfix/issue-123`
    *   `docs/update-readme`

4.  **Make your changes**, commit them, and push them to your fork.

5.  **Create a Pull Request** to the `main` branch of the original repository. When creating the PR, make sure to reference the respective issue you are addressing (e.g., "Fixes #123").

## Reporting Bugs

Before submitting a new issue, please search the issue tracker to see if a similar bug has already been reported.

When reporting a bug, please provide the following information:

*   **A clear and descriptive title.**
*   **A detailed description of the problem.** Explain what you were doing and what went wrong.
*   **Steps to reproduce the behavior.**
*   **Expected behavior:** What you expected to happen.
*   **Actual behavior:** What actually happened.
*   **Your environment:** Include your Java version, operating system, and any other relevant details.

## Pull Requests

Pull requests are the best way to propose changes to the codebase. We actively welcome your pull requests.

1.  Fork the repo and create your branch from `main`.
2.  If you've added code that should be tested, add tests.
3.  If you've changed APIs, update the documentation.
4.  Ensure the test suite passes.
5.  Make sure your code lints.
6.  Issue that pull request!

### Simple Fixes

For a simple bug fix, your pull request description should clearly explain the problem you have solved.

**Important Note:** Please avoid submitting pull requests with alternative implementations for existing interfaces unless it is absolutely necessary and has been discussed with the maintainers. While we appreciate the creativity, managing a large number of implementations for the same interface can become unmanageable.

### Introducing a New Concept

When introducing a new concept, feature, or module, your pull request description and any related design documents should clearly address the following points to ensure a smooth review process:

#### 1. What is it for?
Provide a clear and concise explanation of the new concept's purpose.

#### 2. What problem does it solve?
Describe the specific problem or use case that this new concept addresses. What pain points does it alleviate for the community?

#### 3. Observable shortcomings / potential conflicts
Discuss any known limitations, trade-offs, or potential areas of conflict with existing parts of the system. This transparency is crucial for making informed decisions.

#### 4. How does it work?
Explain the inner workings of the concept. This should include a high-level overview of the implementation.

#### 5. Scalability
Assess the scalability of your solution. Can it handle growth in data, traffic, or complexity? Are there any potential bottlenecks?

#### 6. Architecture Diagram
Provide a diagram that illustrates the architecture of the new concept and how it fits into the existing system. You can use tools like Mermaid, PlantUML, or simply attach an image to the pull request.

By providing this information, you help the maintainers and the community understand the value and impact of your contribution. Thank you for your effort and dedication!
