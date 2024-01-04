# FilelessShare

파일없는 파일 공유 시스템


## ?????
기본적으로 URL 파라미터의 제한은 짧지 않다 (크롬 기준 2MB)
<br/>https://2jinishappy.tistory.com/314

이를 이용하여 파일을 Base64로 인코딩 한 값을 이용, 해당 문자열을 다시 디코딩한다면 파일을 불러 올 수 있다.

ex) input.txt -> (base 64)78548615= -> http://127.0.0.1:8080/donwload?data=7856615.... => download

### 그래서 File Base64 Encoder / Decoder 아닌감
네

### 그래도 뭐라도 다른 기능 없나요?
URL Parameter가 제한이 짧지 않아도 제한은 있음 -> 매우 큰 파일의 경우 Base64로 인코딩 된 값의 길이가 URL파라미터 제한을 넘어서기 때문에 이를 Zlib을 이용해 문자열을 압축하여 좀더 큰 파일도 지원할 수 있게 하였음

### 근데 POST 요청 쓰는게 낫지 않나
네

### 왜 만들었나요
https://www.youtube.com/watch?v=pCOBmmJARPE
<br/>기존 URL 파라미터를 이렇게도 활용할 수 있다는 영상을 보고 구현해봤습니다.
<br/>이 프로젝트를 제외하고, light-weight한 데이터, 단순한 데이터를 쿠키 없이 저장할땐 GET의 파라미터를 쓰는것도  때로는 좋은 선택일 것 같습니다.

굳이 다른 사람과의 일부 데이터 공유를 위해 테이블을 만들고 DTO를 반환하는 과정없이 url만 넘겨주면 됨
