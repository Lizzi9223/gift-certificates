            
            function SortByDate(a, b) { 
                return new Date(b.create_date).getTime() - new Date(a.create_date).getTime();
            } 

            var certificates = JSON.parse(localStorage.getItem('certificates'));    
            certificates.sort(SortByDate);


            var listElm = document.querySelector('#coupons');
            var idx = 0;

            
            var loadMore = function() {
                
              for (var i = 0; i < 10; i++, idx++) {
                  
                if(window.searchParam != null){
                    if(!certificates[idx].name.toLowerCase().includes(window.searchParam))
                        continue;
                }       
                  
                if(arguments.length>0){
                    var contains = false;
                    for(var j in certificates[idx].tags){
                        if(arguments[0] == certificates[idx].tags[j]){
                            contains = true;
                            break;
                        }                            
                    }
                    if(!contains)
                        continue;
                }
                  
                if(idx>=certificates.length) idx=0;
                  
                var coupon_wrap = document.createElement('div');
                coupon_wrap.classList.add('coupon-wrap');
                  
                var coupon = document.createElement('div');
                coupon.classList.add('coupon');
                
                var square = document.createElement('div');
                square.classList.add('square');
                  
                var coupon_image = document.createElement('img');
                coupon_image.classList.add('coupon-image');
                coupon_image.src = certificates[idx].image_src;
                square.appendChild(coupon_image);
                  
                var coupon_info = document.createElement('div');
                coupon_info.classList.add('coupon-info');
                  
                  
                  
                var first_line = document.createElement('div');
                first_line.classList.add('first-line'); 
                  
                var coupon_name = document.createElement('div');
                coupon_name.classList.add('coupon-name');
                coupon_name.textContent = certificates[idx].name;
                first_line.appendChild(coupon_name);
                  
                var favourite = document.createElement('div');
                favourite.classList.add('favourite');
                  
                var image = document.createElement('img');
                image.src = "images/favourite.svg";                
                  
                favourite.appendChild(image);
                first_line.appendChild(favourite);
                  
                  
                  
                var second_line = document.createElement('div');
                second_line.classList.add('second-line'); 
                                    
                var description = document.createElement('div');
                description.classList.add('description');
                description.textContent = certificates[idx].description;
                second_line.appendChild(description);
                  
                var expires = document.createElement('div');
                expires.classList.add('expires');
                expires.textContent = 'Expires in ';
                expires.textContent += certificates[idx].duration;
                expires.textContent += ' days';
                second_line.appendChild(expires);
                  
                  
                  
                var third_line = document.createElement('div');
                third_line.classList.add('third-line');                  
                  
                var price = document.createElement('div');
                price.classList.add('price');
                price.textContent = certificates[idx].price;
                third_line.appendChild(price); 
                  
                var add_button = document.createElement('div');
                add_button.classList.add('add-button');
                  
                var input = document.createElement("input");
                input.setAttribute('type', 'submit');
                input.setAttribute('value', 'Add to Cart');
                
                
                add_button.appendChild(input);
                third_line.appendChild(add_button);
                  
                  
                  
                var line = document.createElement('hr');
                  
                  
                  
                coupon_info.appendChild(first_line);
                coupon_info.appendChild(second_line);
                coupon_info.appendChild(line);
                coupon_info.appendChild(third_line);
                  
                coupon.appendChild(square);
                coupon.appendChild(coupon_info);
                coupon_wrap.appendChild(coupon);
                listElm.appendChild(coupon_wrap);
              }
            }
            
            
            function scroll(event){
                if (listElm.scrollTop + listElm.clientHeight >= listElm.scrollHeight) {
                    if(event.currentTarget.tagParam != null && event.currentTarget.tagParam != '') 
                        loadMore(event.currentTarget.tagParam);
                    else loadMore();
                  }
            }

            
            window.addEventListener('scroll', scroll, false);
            
            loadMore();



            var findByTag = function(){
                if(arguments.length>0){
                  idx = 0;        
                  listElm.textContent = '';
                  loadMore(arguments[0]);
                }
                else{
                  idx = 0;        
                  listElm.textContent = '';
                  loadMore();
                }
            }
            
            
            
            // CATEGORIES
            
            
            var tags = JSON.parse(localStorage.getItem('tags'));
            var listTags = document.querySelector('#categories');


            for(var i = 0;  i < tags.length; i++){
                
                var category_wrap = document.createElement('div');
                category_wrap.classList.add('category-wrap');
                
                var category = document.createElement('div');
                category.classList.add('category');

                var square = document.createElement('div');
                square.classList.add('square');

                var image = document.createElement('img');
                image.src = tags[i].image_src;

                var middle = document.createElement('div');
                middle.classList.add('middle');

                var text = document.createElement('div');
                text.textContent = tags[i].name;
                text.classList.add('text');
                text.onclick = function() {
                    findByTag(this.textContent);
                };

                middle.appendChild(text);
                square.appendChild(image);
                square.appendChild(middle);
                category.appendChild(square);
                category_wrap.appendChild(category);
                listTags.appendChild(category_wrap);
            }




