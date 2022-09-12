            var tags = JSON.parse(localStorage.getItem('tags'));
            var searchTagsList = document.querySelector('.dropdown');


            for(var i = 0;  i < tags.length; i++){                
                
                var search_category = document.createElement('li');
                search_category.innerHTML = tags[i].name;
                searchTagsList.appendChild(search_category);
            }



            const items = document.querySelectorAll('.header > .search > ul > li > ul > li:not(#li-dropdown-input)');
            items.forEach(item => {
                item.addEventListener('click',(e)=>{
                    findByTag(e.target.textContent);
                }
                )
            });

            function rotateImage(){
                document.querySelector('.header > .search > ul > li > .dropdown-title > img').style.transform = "rotate(180deg)";
            }

            function rotateImageBack(){
                document.querySelector('.header > .search > ul > li > .dropdown-title > img').style.transform = "rotate(0deg)";
            }


            let typingTimer;                
            let doneTypingInterval = 1000;
            let myInput = document.querySelector('.header .search .search-input');
            let tagInput = document.querySelector('.header .search #dropdown-input');
            window.searchParam = null;

            function searchByName(){
                clearTimeout(typingTimer);
                if (myInput.value) {
                    typingTimer = setTimeout(doneTypingByName, doneTypingInterval);
                }
            }

            function doneTypingByName() {
                window.searchParam = myInput.value.trim().toLowerCase();
                if(myInput.value.trim() == '') window.searchParam = null;
                findByTag();
                window.searchParam = null;
            }

            function searchByTag(){
                clearTimeout(typingTimer);
                if (tagInput.value) {
                    typingTimer = setTimeout(doneTypingByTag, doneTypingInterval);
                }
            }

            function doneTypingByTag() {
                if(tagInput.value.trim() == '') findByTag();
                else findByTag(tagInput.value);
            }