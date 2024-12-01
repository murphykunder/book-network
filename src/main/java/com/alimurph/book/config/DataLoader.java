package com.alimurph.book.config;

import com.alimurph.book.auth.AuthenticationService;
import com.alimurph.book.auth.RegistrationRequest;
import com.alimurph.book.book.Book;
import com.alimurph.book.book.BookRepository;
import com.alimurph.book.feedback.Feedback;
import com.alimurph.book.feedback.FeedbackRepository;
import com.alimurph.book.role.Role;
import com.alimurph.book.role.RoleRepository;
import com.alimurph.book.user.User;
import com.alimurph.book.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(this.getClass());
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final FeedbackRepository feedbackRepository;

    public DataLoader(RoleRepository roleRepository, BookRepository bookRepository, UserRepository userRepository, AuthenticationService authenticationService, FeedbackRepository feedbackRepository) {
        this.roleRepository = roleRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Database initialization - BEGIN");
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Long adminRoleId = roleRepository.save(
                    new Role.Builder()
                            .setName("ADMIN")
                            .setCreatedDate(LocalDateTime.now())
                            .setCreatedBy("initialization")
                            .createRole()
            ).getId();

            log.debug("Created ADMIN role with ID = " + adminRoleId);
        }

        if (roleRepository.findByName("USER").isEmpty()) {
            Long userRoleId = roleRepository.save(
                    new Role.Builder()
                            .setName("USER")
                            .setCreatedDate(LocalDateTime.now())
                            .setCreatedBy("initialization")
                            .createRole()
            ).getId();

            log.debug("Created USER role with ID = " + userRoleId);
        }

        // if (userRepository.findByEmail("murphy@dogmail.com").isEmpty()) {
        //     RegistrationRequest registrationRequest = new RegistrationRequest("Murphy", "Kunder", "murphy@dogmail.com", "password123");
        //     authenticationService.register(registrationRequest);
        //     log.debug("Created user with email murphy@dogmail.com");
        // }

        // if (!(bookRepository.findAll().stream().count() > 0)) {

        //     User owner = userRepository.findByEmail("murphy@dogmail.com").get();

        //     bookRepository.save(new Book.Builder()
        //             .setTitle("Classical Mythology")
        //             .setAuthorName("Mark P. O. Morford")
        //             .setIsbn("656456765615")
        //             .setSynopsis("Offers a comprehensive presentation of the principle myths of Greece and Rome and their relationship to actual history.")
        //             .setOwner(owner)
        //             .setShareable(true)
        //             .setCreatedBy(owner.getId())
        //             .setCreatedDate(LocalDateTime.now())
        //             .createBook()
        //     );

        //     log.debug("Created book 1");

        //     bookRepository.save(new Book.Builder()
        //             .setTitle("Clara Callan")
        //             .setAuthorName("Richard Bruce Wright")
        //             .setIsbn("546831213565")
        //             .setSynopsis("In a small town in Canada, Clara Callan reluctantly takes leave of her sister, Nora, who is bound for New York. It's a time when the growing threat of fascism in Europe is a constant worry, and people escape from reality through radio and the movies. Meanwhile, the two sisters -- vastly different in personality, yet inextricably linked by a shared past -- try to find their places within the complex web of social expectations for young women in the 1930s.")
        //             .setOwner(owner)
        //             .setShareable(true)
        //             .setCreatedBy(owner.getId())
        //             .setCreatedDate(LocalDateTime.now())
        //             .createBook()
        //     );

        //     log.debug("Created book 2");

        //     bookRepository.save(new Book.Builder()
        //             .setTitle("Decision in Normandy")
        //             .setAuthorName("Carlo D'Este")
        //             .setIsbn("3546498132")
        //             .setSynopsis("The battle for Normandy was the most complex and daring military operation in the history of modern warfare. Two years of intense, detailed planning reached its successful conclusion when the Allied forces took the beaches on D-Day. But the seventy-six-day campaign that followed, the Allies' crucial bid for a toehold in western Europe, was one of the bloodiest of the war, and its true story has been concealed in myth. Drawing on a wealth of previously unpublished papers, declassified documents, diaries, and personal interviews, Carlo D'Este has written the first full account of what actually happened in Normandy, how the campaign went wrong, and how it was eventually won. Step-by-step the reader is taken through the Normandy campaign from the earliest days after Dunkirk when Churchill first considered the idea of a cross-channel invasion of France, to the key battles that determined that outcome, with maps clearly explaining the strategy and logistics of each battle.")
        //             .setOwner(owner)
        //             .setShareable(true)
        //             .setCreatedBy(owner.getId())
        //             .setCreatedDate(LocalDateTime.now())
        //             .createBook()
        //     );

        //     log.debug("Created book 3");

        //     bookRepository.save(new Book.Builder()
        //             .setTitle("The Mummies of Urumchi")
        //             .setAuthorName("E. J. W. Barber")
        //             .setIsbn("1348466868")
        //             .setSynopsis("In the museums of Ürümchi, the windswept regional capital of the Uyghur Autonomous Region (also known as Chinese Turkestan), a collection of ancient mummies lies at the center of an enormous mystery. Some of Ürümchi's mummies date back as far as 4,000 years―contemporary with the famous Egyptian mummies but even more beautifully preserved.")
        //             .setOwner(owner)
        //             .setShareable(true)
        //             .setCreatedBy(owner.getId())
        //             .setCreatedDate(LocalDateTime.now())
        //             .createBook()
        //     );

        //     log.debug("Created book 4");

        //     bookRepository.save(new Book.Builder()
        //             .setTitle("The Kitchen God's Wife")
        //             .setAuthorName("Amy Tan")
        //             .setIsbn("78353548651")
        //             .setSynopsis("Winnie and Helen have kept each other's worst secrets for more than fifty years. Now, because she believes she is dying, Helen wants to expose everything. And Winnie angrily determines that she must be the one to tell her daughter, Pearl, about the past—including the terrible truth even Helen does not know. And so begins Winnie's story of her life on a small island outside Shanghai in the 1920s, and other places in China during World War II, and traces the happy and desperate events that led to Winnie's coming to America in 1949.")
        //             .setOwner(owner)
        //             .setShareable(true)
        //             .setCreatedBy(owner.getId())
        //             .setCreatedDate(LocalDateTime.now())
        //             .createBook()
        //     );

        //     log.debug("Created book 5");
        // }

        // if(!(feedbackRepository.findAll().stream().count() > 0)){
        //     bookRepository.findAll().stream().forEach((book) -> {
        //         feedbackRepository.save(new Feedback.Builder()
        //                         .setRating(3)
        //                         .setComment("I enjoyed reading this book. Would recommend it to my friends!")
        //                         .setBook(book)
        //                         .setCreatedBy(book.getOwner().getId())
        //                         .setCreatedDate(LocalDateTime.now())
        //                 .createFeedback()
        //         );
        //     });
        // }

        log.info("Database initialization - END");
    }
}
