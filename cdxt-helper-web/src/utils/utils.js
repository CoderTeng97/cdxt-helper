
/**
 * 组建新的路由表
 */
let routersList = []; //新的路由表
export function getRoutersInfo(userRoles, routesArray) {
    let newRoutesArray = routesArray
    if(typeof routesArray === 'object') {
        if(routesArray.children) {
            newRoutesArray = newRoutesArray.children
        }
    }
    if(newRoutesArray.length > 0) {
        newRoutesArray.forEach(item => {
            if(item.roles) {
                let rolesList = item.roles;
                //有孩子
                if(item.children) {
                    getRoutersInfo(userRoles,item)
                } else {
                    //无孩子节点
                    rolesList.forEach(rolesItem => {
                        if(userRoles === rolesItem) {
                            routersList.push(routesArray)
                        }
                    });
                }
            }else if(item.children){
                 //有孩子节点
                getRoutersInfo(userRoles,item)
            }else {
                routersList.push(item)
            }
       });
    }
    return routersList;
}

export const unique = (arr) => {
    if (Array.hasOwnProperty('from')) {
        return Array.from(new Set(arr));
    } else {
        var n = {}, r = [];
        for (var i = 0; i < arr.length; i++) {
            if (!n[arr[i]]) {
                n[arr[i]] = true;
                r.push(arr[i]);
            }
        }
        return r;
    }
}