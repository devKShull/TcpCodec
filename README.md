# Android TCP codec

> 이 모듈은 TCP 패킷을 encode decode 하기 위한 모듈입니다.

이 라이브러리의 이름은 Android TCP codec 이지만 codec 자체는 범용 kotlin library 입니다.

프로젝트는 Android 에서의 테스트를 위해 Android 프로젝트 아래에 모듈이 구현되지만 실제 모듈 자체는 kotlin 및 java 플랫폼 모두에서 사용가능합니다.

## How to use

1. Project 의 Setting.gradle 파일에 추가
   setting.gradle
   ``` 
   dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
    ```

   kotlin dsl 인 경우

   setting.gradle.kts
    ``` 
    dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { setUrl("https://jitpack.io") }
		}
	}
    ```
2. 사용할 패키지의 build.gradle 에 추가

   build.gradle
   ```
    dependencies {
	        implementation 'com.github.devKShull:TcpCodec:Tag'
	}
   ```

   build.gradle.kts
   ```
   dependencies {
	        implementation("com.github.devKShull:TcpCodec:Tag")
	}
   ```

## HandShake

| type   | name           | length              | binary                        |
|--------|----------------|---------------------|-------------------------------|
| Header | Length         | 4 byte              | 32 bit                        |
| Header | HandShake Type | 1 byte              | 8 bit                         |
| Header | Type           | 1 byte              | 8 bit                         |
| Header | Mode           | 1 byte              | 8 bit                         |
| Header | Padding        | 1 byte              | 8 bit                         |
| Body   | IV Parameter   | 16 byte             | 128 bit                       |
| Body   | Symmetric Key  | 16 or 24 or 32 byte | 128 bit or 192 bit or 256 bit |

### HandShakeHeader

1. HandshakeType

- required `RSA 1024`, other failure

| name     | hex  |
|----------|------|
| NONE     | 0x00 |
| RSA 1024 | 0x01 |
| RSA 2048 | 0x02 |
| RSA 4096 | 0x03 |

2. Type

- encryption type
- require `AES`, other failure

| name | hex  |
|------|------|
| NONE | 0x00 |
| AES  | 0x01 |

2. Mode

- encryption mode
- require `CBC`, other failure

| name | hex  |
|------|------|
| NONE | 0x00 |
| ECB  | 0x01 |
| CBC  | 0x02 |
| CFB  | 0x03 |
| OFB  | 0x04 |
| CTR  | 0x05 |

3. Padding

- encryption Padding
- require `PKCS 7 PADDING`, other failure

| name   | hex  |
|--------|------|
| NONE   | 0x00 |
| PKCS#5 | 0x01 |
| PKCS#7 | 0x02 |

4. IV Parameter

- require 16 byte fixed length.

5. Symmetric Key

- 16 or 24 or 32 byte variable length.

## Message

| type   | name     | length   | binary   |
|--------|----------|----------|----------|
| Header | Length   | 4 byte   | 32 bit   |
| Header | ID       | 8 byte   | 64 bit   |
| Header | Type     | 1 byte   | 8 bit    |
| Header | Status   | 1 byte   | 8 bit    |
| Header | Encoding | 1 byte   | 8 bit    |
| Header | Reserved | 1 byte   | 8 bit    |
| Body   | Body     | variable | variable |

더 상세한 정보는 [ppzxc](https://github.com/ppzxc/codec) 에서 확인하세요