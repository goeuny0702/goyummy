const swiper = new Swiper('.swiper-container', {
    // Optional parameters
    loop: true,
    slidesPerView: 1,
    // spaceBetween: 10,
    // If we need pagination
    pagination: {
      el: '.swiper-pagination',
      clickable: true
    },
    autoplay: {
      delay: 3000, // 3초마다 자동 재생
      disableOnInteraction: false // 사용자 상호 작용 후에도 자동 재생 유지
    }, 
    mousewheel: true, // 마우스 휠로 슬라이드 이동 가능
    centeredSlides: true, // 슬라이드 중앙 정렬
  });