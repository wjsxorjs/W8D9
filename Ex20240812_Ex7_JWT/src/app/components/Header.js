
import React from 'react'
import Link from "next/link";
import { cookies } from 'next/headers';

export default function Header() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get("accessToken");

  return (
    <header className="header">
      <nav className="navbar">
        <Link href="/">홈</Link>
        <Link href="/members">회원목록</Link>
        <Link href="/bbs">게시판</Link>
      </nav>
      <div className="fr">
        {
          accessToken == null
          ?
          <>
            <Link href="/members/signin">로그인</Link>
            <Link href="/members/signup">회원가입</Link>
          </>
          :
          <>
            <Link href="/members/signout">로그아웃</Link>
            <Link href="/members/mypage">마이페이지</Link>
          </>
        }
      </div>
    </header>
  )
}
