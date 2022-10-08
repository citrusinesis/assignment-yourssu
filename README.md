# 2022 2학기 YourSSU 백엔드 지원과제
> RESTful한 블로그 API 서비스 만들기

## Caution⚠️
* 뷰는 만들 필요 없습니다.👌
* 빌드는 `gradle`로 하고, `코틀린`을 사용하고, `Java11`로 만들어주세요.👌
* 예외처리를 하실 수 있으면 상황에 맞게 적절히 처리해주세요.👌
* ERROR Response Example
```JSON
{
  "time": "2022-09-21T18:49:06.5964525",
  "status": "BAD_REQUEST",
  "message": "게시글을 찾을 수 없습니다.",
  "requestURI": "/comment/1234"
}
```
* 테스트 코드를 작성하실 수 있다면 작성해주세요.
* 필요한 라이브러리는 자유롭게 쓰시면 됩니다.👌
* 데이터베이스는 MySQL을 사용하시는 것을 추천합니다.👌

---
## Feature✨
### 회원가입👌
> RequestURI: /user/create

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD",
    "username": "citrusinesis"
}
```
Response
```json
{
    "email": "jihojiho2003@gmail.com",
    "username": "citrusinesis"
}
```
* `BCryptPasswordEncoder` 적용
* Exception❗
  - `EMAIL_WRONG_FORMAT`
  - `DUPLICATED_EMAIL_ADDRESS`
  - `DUPLICATED_USERNAME`

### 게시글 작성하기👌
> RequestURI: /article/create

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD",
    "title": "TITLE",
    "content": "CONTENT"
}
```
Response
```json
{
    "articleId": 1,
    "email": "jihojiho2003@gmail.com",
    "title": "TITLE",
    "content": "CONTENT"
}
```
* `Title`, `Content` 유효성 검사👌
* Exception❗
    - `USER_NOT_FOUND`
    - `LOGIN_FAIL`
    - `FIELD_CANNOT_BE_NULL`

### 게시글 수정하기👌
> RequestURI: /article/modify/1

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD",
    "title": "title",
    "content": "content"
}
```
Response
```json
{
    "articleId": 1,
    "email": "jihojiho2003@gmail.com",
    "title": "title",
    "content": "content"
}
```
* `PathVariable` 적용 (`articleId`)
* `Title`, `Content` 유효성 검사👌
* Exception❗
  - `USER_NOT_FOUND`
  - `LOGIN_FAIL`
  - `ARTICLE_NOT_FOUND`
  - `FIELD_CANNOT_BE_NULL`
  - `USER_NOT_MATCH`

### 댓글 작성하기👌
> RequestURI: /comment/create/1

Request
```json
{
   "email": "jihojiho2003@gmail.com",
   "password": "PASSWORD",
    "content": "content"
}
```
Response
```json
{
    "commentId": 1,
    "email": "jihojiho2003@gmail.com",
    "content": "content"
}
```
* `PathVariable` 적용 (`articleId`)
* `Title`, `Content` 유효성 검사👌
* Exception❗
  - `USER_NOT_FOUND`
  - `LOGIN_FAIL`
  - `ARTICLE_NOT_FOUND`
  - `FIELD_CANNOT_BE_NULL`

### 댓글 수정하기👌
> RequestURI: /comment/modify/1/1

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD",
    "content": "CONTENT"
}
```
Response
```json
{
    "commentId": 1,
    "email": "jihojiho2003@gmail.com",
    "content": "CONTENT"
}
```
* `PathVariable` 적용 (`articleId`, `commentId`)
* `Title`, `Content` 유효성 검사👌
* Exception❗
  - `USER_NOT_FOUND`
  - `LOGIN_FAIL`
  - `ARTICLE_NOT_FOUND`
  - `COMMENT_NOT_FOUND`
  - `FIELD_CANNOT_BE_NULL`
  - `USER_NOT_MATCH`

### 댓글 삭제하기👌
> RequestURI: /comment/delete/1/1

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD"
}
```
Response
```
200 "OK"
```
* `PathVariable` 적용 (`articleId`, `commentId`)
* Exception❗
  - `USER_NOT_FOUND`
  - `LOGIN_FAIL`
  - `ARTICLE_NOT_FOUND`
  - `COMMENT_NOT_FOUND`
  - `USER_NOT_MATCH`

### 게시글 삭제하기👌
> RequestURI: /article/delete/1

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD"
}
```
Response
```
200 "OK"
```
* `PathVariable` 적용 (`articleId`)
* 해당 게시글에 있는 댓글 모두 삭제👌
* Exception❗
  - `USER_NOT_FOUND`
  - `LOGIN_FAIL`
  - `ARTICLE_NOT_FOUND`
  - `USER_NOT_MATCH`

### 유저 삭제하기👌
> RequestURI: /user/delete/

Request
```json
{
    "email": "jihojiho2003@gmail.com",
    "password": "PASSWORD"
}
```
Response
```
200 "OK"
```
* 해당 회원이 쓴 게시글, 해당 회원이 쓴 댓글들 모두 삭제👌
* Exception❗
  - `USER_NOT_FOUND`
  - `LOGIN_FAIL`