## 소개
러너들을 위한 플랫폼 서비스 잇츠러닝타임의 서버 프로젝트입니다.<br>
running-schedule 서버는 잇츠러닝타임 서비스의 주요 기능인 마라톤 대회 정보를 제공하는 서버입니다.<br>
마라톤 대회 정보는 크롤링을 통해 수집하며, 이를 통해 사용자들은 마라톤 대회 정보를 제공받을 수 있습니다.<br>

## 지원 플랫폼
- iOS, AOS, Web

## Ground Rule
- PR open 전 반드시 기능이 정상적으로 동작하는지 확인 후 코드리뷰를 요청합니다.
- squash commit & merge를 기반으로 브랜치를 관리합니다.
- 빠른 배포 주기를 위한 trunk-based development를 기반으로 브랜치를 관리합니다.

## 컨벤션
### git commit convention
- feat: 새로운 기능 추가 비즈니스 로직 변경
- fix: 버그 수정
- docs: 문서 수정
- style: 코드 포맷팅, 세미콜론 누락 등의 스타일 수정
- refactor: 코드 리팩토링
- test: 테스트 코드, 리팩토링 테스트 코드 추가
- chore: 빌드 업무 수정, 패키지 매니저 수정
- build: 프로젝트의 빌드 시스템 수정
- ci: CI관련 설정 수정
- perf: 성능 개선
- revert: 작업 되돌리기
- temp: 임시 작업
- etc: 기타 변경사항