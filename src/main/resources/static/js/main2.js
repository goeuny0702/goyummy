const swiper = new Swiper('.swiper', {
    // Optional parameters
    direction: 'horizontal',
    slidesPerView: 2,
    spaceBetween: 10,
    loop: true,

    // If we need pagination
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
  

    observer: true, // DOM 변경 감지
    observeParents: true, // 부모 요소 변경 감지

  });

  // 스크롤 애니메이션 코드
  document.addEventListener("DOMContentLoaded", function() {
    const sections = document.querySelectorAll('.scroll-section');
    let rafScheduled = false;

    function revealSections() {
        rafScheduled = false;

        for (const section of sections) {
            const sectionTop = section.getBoundingClientRect().top;
            const windowHeight = window.innerHeight || document.documentElement.clientHeight;

            if (sectionTop <= windowHeight * 0.7) {
                section.classList.add('visible');
            } else {
                section.classList.remove('visible');
            }
        }
    }

    function requestAnimation() {
        if (rafScheduled) return;
        rafScheduled = true;
        requestAnimationFrame(revealSections);
    }

    window.addEventListener('scroll', requestAnimation);
    revealSections();
});
