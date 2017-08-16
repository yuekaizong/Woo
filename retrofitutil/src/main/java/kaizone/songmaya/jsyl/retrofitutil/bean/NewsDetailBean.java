package kaizone.songmaya.jsyl.retrofitutil.bean;

import java.util.List;

/**
 */
public class NewsDetailBean {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">为什么不能用水力发电再加上电解水来反应出氢气来作为燃料？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic1.zhimg.com/f1aa3050082d76ffdfb55f650323eeb0_is.jpg">
     <span class="author">Kevin Zhang</span>
     </div>

     <div class="content">
     <p>题主谈到的是电解水生氢，那我就把答案的讨论限定在这个范围内。但是需要强调的是<strong>：当前制氢的</strong><strong>主要</strong><strong>方法还是对碳氢化合物进行蒸汽改质。</strong></p>
     <p>题目结尾的那句结论（电解水生成的氢气和氧气&ldquo;百利而无一害&rdquo;）或许是对的，而且我可以很明确的说相应的硬件技术（比如 electrolyser）也已经开发出来了。这个话题在学术界也很火，简称 Power2Gas。欧盟甚至投了几千万去专门研究氢能源应用，比如我参与的 H2ME 项目。</p>
     <p><strong>那么问题来了，这么&ldquo;好&rdquo;的东西为什么没有得到大规模推广呢？<br /></strong></p>
     <p>究其原因还是&ldquo;<strong>成本</strong>&rdquo;二字。以下是来源于美国能源部的报告里的氢能源生产路线图[1]，其中红圈代表了主流的电解水生氢技术:</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/v2-46d9121c92863be6162f2129d6d92951_b.jpg" alt="" /></p>
     <p>现阶段电力系统和氢能源最直接的联系之一就是使用氢能源的交通设施，比如燃料电池汽车（Fuel cell vehicle）。但相较于直接充电的电动汽车，如果氢能源汽车的燃料来源是电解水，则增加了一道工序，那也就意味着相应的能量损耗和额外的设备需求，造成了成本上升。</p>
     <p>那么我们为什么还要研究电解水技术呢？</p>
     <ul>
     <li>首先燃料电池汽车是真实存在，比如经典的丰田 Mirai。那么随着燃料汽车的推广（虽然相对缓慢），加氢的需求必然是越来越大的。相较于答案开头提到的工业提炼方式，eletrolyser 的两个主要原料（水和电力）基本都可以就地取材。这也就避免了氢燃料在工业提炼后到加氢站的运输环节。</li>
     <li>其次，当前电池领域在技术革新上不可否认的出现了一些瓶颈。从能量密度的角度上来说，氢储能的这一参数会更高。所以当我们把能源的生产成本与其对应的储能成本加在一起去考虑的话，氢能源在未来也未必不可与电力配电池一战。另外从充放速度上来考虑，电池恐怕也无法与氢气和汽油这类燃料的储存设备相匹敌。那么在能源互联的大背景下，电力储能方式的多样化也是一种必然的选择。</li>
     <li>氢气的使用也是多样化的，比如合成甲烷混入天然气网络。在英国就有 ITM Power，Nantional Grid 和 National Gas 合作拿到了 ofgem 的 funding，对电解水生氢和加氢站的建设在做一些示范性的商业项目。</li>
     </ul>
     <p><strong>上面简述了电解水生氢技术上的可行性。只是相较于直接使用电力，电解水看起来貌似还是有些贵。</strong></p>
     <p><strong>那么在下文里我来解释一下这种电解水生氢的商业运营模式要在什么样的场景下才能经济层面上可行。（以下结论是根据我们组内之前的一些日常讨论得出的，可能会存在不严谨的地方，欢迎知友们指出。）</strong></p>
     <p>首先需要强调，电解水生氢的加氢站成本主要来源于三部分：</p>
     <ol>
     <li><strong>电解设备的平摊成本（考虑到设备的使用寿命）。</strong></li>
     <li><strong>加氢站的加氢设备和储氢设备成本（也需要考虑到设备的使用寿命）。</strong></li>
     <li><strong>发电和输配电成本。</strong></li>
     </ol>
     <p>可以明确地是前两点属于设备成本，那只有技术提升或是大规模生产才能进一步降低成本。所以当前&ldquo;最容易&rdquo;降低的是第三点，也就是用电成本。</p>
     <p>根据我们组另外一个 RA 的调研结果想要让电解生氢变得经济可行，首先需要将其用电成本降低到接近零才行。那么这一成本可能接近零么？是有可能的。比如丹麦这种风力发电资源丰富的国家，经常会出现电力富余的情况下，甚至出现负电价。那么在这种环境下与其切掉多余的风电，不如将它们利用起来，比如说用来电解水生氢。</p>
     <p>关于题主所提到的水力发电，为什么我们不太用来电解水？因为水力发电本身具备一定的储能特性，所以它的输出调节能力比风电和光伏要强上不少。这使得他们跟火电类似，具备发电议价能力。那么从水电站的角度上来说不太可能将自己的电免费送出。</p>
     <p>所以综上所述，概括到以下三点：</p>
     <ol>
     <li><strong>电解水生氢？技术可行。</strong></li>
     <li><strong>水电拿来电解水生氢？成本不允许。</strong></li>
     <li><strong>什么情况下电解水生氢经济性上也可行？当电力系统中经常出现过剩的清洁能源发电情况，成本上或许可行。</strong></li>
     </ol>
     <p>以上，谢谢。</p>
     <p>另外推荐师兄在 P2G 方向的几篇文章。有兴趣的可以读一读（P.S. <strong>师兄 Stephen Clegg 有曼大数学系和电力系的双博士学位哦...是一个一个读完的....我这辈子除他以外没见过第二个 Dr^2</strong>）</p>
     <ul>
     <li><a href="https://www.researchgate.net/publication/285630259_Integrated_Electrical_and_Gas_Network_Flexibility_Assessment_in_Low-Carbon_Multi-Energy_Systems">Integrated_Electrical_and_Gas_Network_Flexibility_Assessment_in_Low-Carbon_Multi-Energy_Systems</a></li>
     <li><a href="https://www.researchgate.net/publication/277888684_Integrated_Modeling_and_Assessment_of_the_Operational_Impact_of_Power-to-Gas_P2G_on_Electrical_and_Gas_Transmission_Networks">Integrated_Modeling_and_Assessment_of_the_Operational_Impact_of_Power-to-Gas_P2G_on_Electrical_and_Gas_Transmission_Networks</a></li>
     <li><a href="https://www.researchgate.net/publication/296479100_Storing_renewables_in_the_gas_network_Modelling_of_power-to-gas_seasonal_storage_flexibility_in_low-carbon_power_systems">Storing_renewables_in_the_gas_network_Modelling_of_power-to-gas_seasonal_storage_flexibility_in_low-carbon_power_systems</a></li>
     </ul>
     <p>Reference list:</p>
     <p>[1]<a href="https://energy.gov/sites/prod/files/2014/03/f12/webinarslides052311_pemelectrolysis_overview.pdf">https://energy.gov/sites/prod/files/2014/03/f12/webinarslides052311_pemelectrolysis_overview.pdf</a></p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/53838421">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div>
     * image_source : Yestone.com 版权图片库
     * title : 生产零污染燃料的技术，其实早就有了
     * image : http://pic4.zhimg.com/e75a2f9b1c6c4c99a67b4f81701b0e6f.jpg
     * share_url : http://daily.zhihu.com/story/9087496
     * js : []
     * ga_prefix : 122219
     * images : ["http://pic3.zhimg.com/1d23dc030b0738228cc214c32611f7d2.jpg"]
     * type : 0
     * id : 9087496
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
