package lift.cookbook.snippet

import net.liftweb.util.BindHelpers._

import scala.collection.immutable

object Table {

  def dynamic = {
    val headers = (1 to 10)
    // creates a 10 x 10 matrix 

    val table: immutable.Seq[immutable.IndexedSeq[Int]] = headers.map(n => (1 to 10).map(in => n * in))
    //this is the 10*10 table:
    //    Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    //    Vector(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
    //    Vector(3, 6, 9, 12, 15, 18, 21, 24, 27, 30)

//
    <div id="main" class="lift:surround?with=default;at=content">
      <table data-lift="Table.dynamic">
        <thead>
          <tr>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td></td> 
          </tr>
        </tbody>
      </table>
    </div>
    
    


    //    --> result
    <thead>
      <tr>
        <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th>
      </tr>
    </thead>
      <tbody>
        <tr>
          <td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td>
        </tr><tr>
        <td>2</td><td>4</td><td>6</td><td>8</td><td>10</td><td>12</td><td>14</td><td>16</td><td>18</td><td>20</td>
      </tr><tr>
        <td>3</td><td>6</td><td>9</td><td>12</td><td>15</td><td>18</td><td>21</td><td>24</td><td>27</td><td>30</td>
      </tr><tr>
        <td>4</td><td>8</td><td>12</td><td>16</td><td>20</td><td>24</td><td>28</td><td>32</td><td>36</td><td>40</td>
      </tr><tr>
        <td>5</td><td>10</td><td>15</td><td>20</td><td>25</td><td>30</td><td>35</td><td>40</td><td>45</td><td>50</td>
      </tr><tr>
        <td>6</td><td>12</td><td>18</td><td>24</td><td>30</td><td>36</td><td>42</td><td>48</td><td>54</td><td>60</td>
      </tr><tr>
        <td>7</td><td>14</td><td>21</td><td>28</td><td>35</td><td>42</td><td>49</td><td>56</td><td>63</td><td>70</td>
      </tr><tr>
        <td>8</td><td>16</td><td>24</td><td>32</td><td>40</td><td>48</td><td>56</td><td>64</td><td>72</td><td>80</td>
      </tr><tr>
        <td>9</td><td>18</td><td>27</td><td>36</td><td>45</td><td>54</td><td>63</td><td>72</td><td>81</td><td>90</td>
      </tr><tr>
        <td>10</td><td>20</td><td>30</td><td>40</td><td>50</td><td>60</td><td>70</td><td>80</td><td>90</td><td>100</td>
      </tr>
      </tbody>

    //1st: & operator chain binding operations
    //2rd: nesting of bindings

    "th *" #> headers &
      "tbody tr * " #> table.map {
        r => {
          "td *" #> r
        }
      }

  }
}

